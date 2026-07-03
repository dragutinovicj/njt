package rs.acflash.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rs.acflash.dto.impl.StrucnostDto;
import rs.acflash.entity.impl.Strucnost;

class StrucnostMapperTest {

    private StrucnostMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new StrucnostMapper();
    }

    @Test
    void testToDto() {
        Strucnost s = new Strucnost(1L, "Kondicioni trening", "Opis strucnosti", null);

        StrucnostDto dto = mapper.toDto(s);

        assertEquals(1L, dto.getIdStrucnost());
        assertEquals("Kondicioni trening", dto.getNaziv());
        assertEquals("Opis strucnosti", dto.getOpis());
    }

    @Test
    void testToEntity() {
        StrucnostDto dto = new StrucnostDto(2L, "Tehnika trcanja", "Opis");

        Strucnost s = mapper.toEntity(dto);

        assertEquals(2L, s.getIdStrucnost());
        assertEquals("Tehnika trcanja", s.getNaziv());
        assertEquals("Opis", s.getOpis());
    }

    @Test
    void testToDtoNullId() {
        Strucnost s = new Strucnost(null, "Rehabilitacija", "Opis", null);

        StrucnostDto dto = mapper.toDto(s);

        assertNull(dto.getIdStrucnost());
    }
}
