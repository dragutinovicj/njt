package rs.acflash.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rs.acflash.dto.impl.TrenerDto;
import rs.acflash.entity.impl.Trener;

class TrenerMapperTest {

    private TrenerMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new TrenerMapper();
    }

    @Test
    void testToDto() {
        Trener t = new Trener(1L, "Petar", "Petrovic", "petar@fon.rs", "sifra123", null);

        TrenerDto dto = mapper.toDto(t);

        assertEquals(1L, dto.getIdTrener());
        assertEquals("Petar", dto.getIme());
        assertEquals("Petrovic", dto.getPrezime());
        assertEquals("petar@fon.rs", dto.getKorisnickoIme());
        assertEquals("sifra123", dto.getLozinka());
    }

    @Test
    void testToEntity() {
        TrenerDto dto = new TrenerDto(2L, "Jovan", "Jovanovic", "jovan@fon.rs", "lozinka1");

        Trener t = mapper.toEntity(dto);

        assertEquals(2L, t.getIdTrener());
        assertEquals("Jovan", t.getIme());
        assertEquals("Jovanovic", t.getPrezime());
        assertEquals("jovan@fon.rs", t.getKorisnickoIme());
        assertEquals("lozinka1", t.getLozinka());
    }
}
