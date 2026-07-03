package rs.acflash.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rs.acflash.dto.impl.VezbaDto;
import rs.acflash.entity.impl.Vezba;

class VezbaMapperTest {

    private VezbaMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new VezbaMapper();
    }

    @Test
    void testToDto() {
        Vezba v = new Vezba(1L, "Cucanj", "Snaga", "Opis vezbe", 5);

        VezbaDto dto = mapper.toDto(v);

        assertEquals(1L, dto.getIdVezba());
        assertEquals("Cucanj", dto.getNaziv());
        assertEquals("Snaga", dto.getTip());
        assertEquals("Opis vezbe", dto.getOpis());
        assertEquals(5, dto.getTezina());
    }

    @Test
    void testToEntity() {
        VezbaDto dto = new VezbaDto(2L, "Sprint 100m", "Brzina", "Opis", 8);

        Vezba v = mapper.toEntity(dto);

        assertEquals(2L, v.getIdVezba());
        assertEquals("Sprint 100m", v.getNaziv());
        assertEquals("Brzina", v.getTip());
        assertEquals("Opis", v.getOpis());
        assertEquals(8, v.getTezina());
    }

    @Test
    void testToDtoNullId() {
        Vezba v = new Vezba(null, "Skip A", "Tehnika", "Opis", 3);

        VezbaDto dto = mapper.toDto(v);

        assertNull(dto.getIdVezba());
    }
}
