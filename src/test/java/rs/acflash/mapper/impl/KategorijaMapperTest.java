package rs.acflash.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rs.acflash.dto.impl.KategorijaDto;
import rs.acflash.entity.impl.Kategorija;

class KategorijaMapperTest {

    private KategorijaMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new KategorijaMapper();
    }

    @Test
    void testToDto() {
        Kategorija k = new Kategorija(1L, "Sprint", "Kratkoprugaske discipline", null);

        KategorijaDto dto = mapper.toDto(k);

        assertEquals(1L, dto.getIdKategorija());
        assertEquals("Sprint", dto.getNaziv());
        assertEquals("Kratkoprugaske discipline", dto.getOpis());
    }

    @Test
    void testToEntity() {
        KategorijaDto dto = new KategorijaDto(2L, "Bacanja", "Disciplina bacanja");

        Kategorija k = mapper.toEntity(dto);

        assertEquals(2L, k.getIdKategorija());
        assertEquals("Bacanja", k.getNaziv());
        assertEquals("Disciplina bacanja", k.getOpis());
    }

    @Test
    void testToDtoNullId() {
        Kategorija k = new Kategorija(null, "Skokovi", "Disciplina skokova", null);

        KategorijaDto dto = mapper.toDto(k);

        assertNull(dto.getIdKategorija());
    }

    @Test
    void testToEntityNullId() {
        KategorijaDto dto = new KategorijaDto(null, "Skokovi", "Disciplina skokova");

        Kategorija k = mapper.toEntity(dto);

        assertNull(k.getIdKategorija());
    }
}
