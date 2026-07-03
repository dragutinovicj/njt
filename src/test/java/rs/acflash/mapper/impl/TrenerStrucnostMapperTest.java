package rs.acflash.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rs.acflash.dto.impl.TrenerStrucnostDto;
import rs.acflash.entity.impl.Strucnost;
import rs.acflash.entity.impl.Trener;
import rs.acflash.entity.impl.TrenerStrucnost;
import rs.acflash.entity.impl.TrenerStrucnostId;

class TrenerStrucnostMapperTest {

    private TrenerStrucnostMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new TrenerStrucnostMapper();
    }

    @Test
    void testToDto() {
        TrenerStrucnostId id = new TrenerStrucnostId(1L, 2L);
        Trener trener = new Trener(1L);
        Strucnost strucnost = new Strucnost(2L);
        TrenerStrucnost ts = new TrenerStrucnost(id, trener, strucnost, "Sertifikovan trener");

        TrenerStrucnostDto dto = mapper.toDto(ts);

        assertEquals(1L, dto.getIdTrener());
        assertEquals(2L, dto.getIdStrucnost());
        assertEquals("Sertifikovan trener", dto.getOdlikovanje());
    }

    @Test
    void testToDtoBezVeza() {
        TrenerStrucnost ts = new TrenerStrucnost(new TrenerStrucnostId(1L, 2L), null, null, "Opis");

        TrenerStrucnostDto dto = mapper.toDto(ts);

        assertNull(dto.getIdTrener());
        assertNull(dto.getIdStrucnost());
    }

    @Test
    void testToEntity() {
        TrenerStrucnostDto dto = new TrenerStrucnostDto(1L, 2L, "Odlikovanje");

        TrenerStrucnost ts = mapper.toEntity(dto);

        assertNotNull(ts.getId());
        assertEquals(1L, ts.getTrener().getIdTrener());
        assertEquals(2L, ts.getStrucnost().getIdStrucnost());
        assertEquals("Odlikovanje", ts.getOdlikovanje());
    }
}
