package rs.acflash.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rs.acflash.dto.impl.StavkaPlanaDto;
import rs.acflash.entity.impl.PlanTreninga;
import rs.acflash.entity.impl.StavkaPlana;
import rs.acflash.entity.impl.StavkaPlanaId;
import rs.acflash.entity.impl.Vezba;

class StavkaPlanaMapperTest {

    private StavkaPlanaMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new StavkaPlanaMapper();
    }

    @Test
    void testToDto() {
        StavkaPlanaId id = new StavkaPlanaId(1L, 10L);
        PlanTreninga plan = new PlanTreninga(10L);
        Vezba vezba = new Vezba(4L);
        StavkaPlana stavka = new StavkaPlana(id, plan, vezba, 3, 12, 5);

        StavkaPlanaDto dto = mapper.toDto(stavka);

        assertEquals(10L, dto.getIdPlan());
        assertEquals(1L, dto.getRb());
        assertEquals(4L, dto.getIdVezba());
        assertEquals(3, dto.getBrojSerija());
        assertEquals(12, dto.getBrojPonavljanja());
        assertEquals(5, dto.getIntenzitet());
    }

    @Test
    void testToDtoBezPlanaIVezbe() {
        StavkaPlana stavka = new StavkaPlana(new StavkaPlanaId(1L, 10L), null, null, 3, 12, 5);

        StavkaPlanaDto dto = mapper.toDto(stavka);

        assertNull(dto.getIdPlan());
        assertNull(dto.getIdVezba());
    }

    @Test
    void testToEntity() {
        StavkaPlanaDto dto = new StavkaPlanaDto(10L, 1L, 4L, 3, 12, 5);

        StavkaPlana stavka = mapper.toEntity(dto);

        assertEquals(1L, stavka.getId().getRb());
        assertEquals(10L, stavka.getId().getIdPlan());
        assertEquals(10L, stavka.getPlan().getIdPlan());
        assertEquals(4L, stavka.getVezba().getIdVezba());
        assertEquals(3, stavka.getBrojSerija());
        assertEquals(12, stavka.getBrojPonavljanja());
        assertEquals(5, stavka.getIntenzitet());
    }
}
