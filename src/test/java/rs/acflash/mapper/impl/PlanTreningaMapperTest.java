package rs.acflash.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rs.acflash.dto.impl.PlanTreningaDto;
import rs.acflash.dto.impl.StavkaPlanaDto;
import rs.acflash.entity.impl.Atleticar;
import rs.acflash.entity.impl.PlanTreninga;
import rs.acflash.entity.impl.StavkaPlana;
import rs.acflash.entity.impl.StavkaPlanaId;
import rs.acflash.entity.impl.Trener;

class PlanTreningaMapperTest {

    private PlanTreningaMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new PlanTreningaMapper(new StavkaPlanaMapper());
    }

    @Test
    void testToDtoBezStavki() {
        Date datum = new Date(0);
        PlanTreninga plan = new PlanTreninga(1L, datum, 3, new Atleticar(5L), new Trener(7L));

        PlanTreningaDto dto = mapper.toDto(plan);

        assertEquals(1L, dto.getIdPlan());
        assertEquals(datum, dto.getDatum());
        assertEquals(3, dto.getIntenzitet());
        assertEquals(5L, dto.getIdAtleticar());
        assertEquals(7L, dto.getIdTrener());
        assertNull(dto.getStavke());
    }

    @Test
    void testToDtoSaStavkama() {
        PlanTreninga plan = new PlanTreninga(1L, new Date(0), 3, new Atleticar(5L), new Trener(7L));
        StavkaPlana stavka = new StavkaPlana(new StavkaPlanaId(1L, 1L), plan, null, 3, 10, 2);
        plan.setStavke(List.of(stavka));

        PlanTreningaDto dto = mapper.toDto(plan);

        assertEquals(1, dto.getStavke().size());
        assertEquals(3, dto.getStavke().get(0).getBrojSerija());
    }

    @Test
    void testToDtoBezAtleticaraITrenera() {
        PlanTreninga plan = new PlanTreninga(1L, new Date(0), 3, null, null);

        PlanTreningaDto dto = mapper.toDto(plan);

        assertNull(dto.getIdAtleticar());
        assertNull(dto.getIdTrener());
    }

    @Test
    void testToEntityBezStavki() {
        PlanTreningaDto dto = new PlanTreningaDto(1L, new Date(0), 4, 5L, 7L, null);

        PlanTreninga plan = mapper.toEntity(dto);

        assertEquals(5L, plan.getAtleticar().getIdAtleticar());
        assertEquals(7L, plan.getTrener().getIdTrener());
        assertNull(plan.getStavke());
    }

    @Test
    void testToEntitySaStavkamaPostavljaRoditelja() {
        StavkaPlanaDto stavkaDto = new StavkaPlanaDto(1L, 1L, 9L, 3, 10, 2);
        PlanTreningaDto dto = new PlanTreningaDto(1L, new Date(0), 4, 5L, 7L, List.of(stavkaDto));

        PlanTreninga plan = mapper.toEntity(dto);

        assertEquals(1, plan.getStavke().size());
        assertTrue(plan.getStavke().get(0).getPlan() == plan);
    }
}
