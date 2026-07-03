package rs.acflash.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import rs.acflash.dto.impl.AtleticarDto;
import rs.acflash.entity.impl.Kategorija;
import rs.acflash.repository.impl.KategorijaRepository;

/**
 * Integracioni test koji proverava relaciju Atleticar -> Kategorija
 * kroz pravu bazu. Ovo je nesto sto ServisTest (sa mokovanim repository-jem)
 * ne moze da uhvati - npr. gresku u JPA mapiranju ili nazivu kolone
 * koja bi bila vidljiva tek pri pravom flush-u/upisu.
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
class AtleticarIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KategorijaRepository kategorijaRepository;

    @Test
    void testKreiranjeAtleticaraSaPostojecomKategorijomPersistujeVezu() throws Exception {
        Kategorija kategorija = new Kategorija(null, "Sprint", "Kratkoprugaske discipline", null);
        kategorijaRepository.save(kategorija);

        AtleticarDto dto = new AtleticarDto(null, "Marko", "Markovic", new Date(0), "M", 180.0, 75.0, 23.1,
                kategorija.getIdKategorija(), null);

        String odgovor = mockMvc.perform(post("/api/atleticar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idKategorija").value(kategorija.getIdKategorija()))
                .andReturn().getResponse().getContentAsString();

        Long idAtleticar = objectMapper.readTree(odgovor).get("idAtleticar").asLong();

        mockMvc.perform(get("/api/atleticar/" + idAtleticar))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ime").value("Marko"))
                .andExpect(jsonPath("$.idKategorija").value(kategorija.getIdKategorija()));
    }

    @Test
    void testAddBezObaveznogPoljaVraca400() throws Exception {
        // ime, prezime, pol, datumRodjenja, kategorija su @NotBlank/@NotNull u AtleticarDto
        AtleticarDto nepotpunDto = new AtleticarDto(null, null, null, null, null, null, null, null, null, null);

        mockMvc.perform(post("/api/atleticar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nepotpunDto)))
                .andExpect(status().isBadRequest());
    }
}
