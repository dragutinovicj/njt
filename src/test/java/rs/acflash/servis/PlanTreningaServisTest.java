package rs.acflash.servis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import rs.acflash.dto.impl.PlanTreningaDto;
import rs.acflash.dto.impl.StavkaPlanaDto;
import rs.acflash.entity.impl.Atleticar;
import rs.acflash.entity.impl.PlanTreninga;
import rs.acflash.entity.impl.Trener;
import rs.acflash.exception.NjtException;
import rs.acflash.mapper.impl.PlanTreningaMapper;
import rs.acflash.mapper.impl.StavkaPlanaMapper;
import rs.acflash.repository.impl.PlanTreningaRepository;

@ExtendWith(MockitoExtension.class)
class PlanTreningaServisTest {

    @Mock
    private PlanTreningaRepository planTreningaRepository;

    private PlanTreningaMapper planTreningaMapper;

    private PlanTreningaServis servis;

    @BeforeEach
    void setUp() {
        planTreningaMapper = new PlanTreningaMapper(new StavkaPlanaMapper());
        servis = new PlanTreningaServis(planTreningaRepository, planTreningaMapper);
    }

    @Test
    void testFindAll() {
        PlanTreninga plan = new PlanTreninga(1L, new Date(0), 3, new Atleticar(1L), new Trener(1L));
        when(planTreningaRepository.findAll()).thenReturn(List.of(plan));

        assertEquals(1, servis.findAll().size());
    }

    @Test
    void testFindById() throws NjtException {
        PlanTreninga plan = new PlanTreninga(1L, new Date(0), 3, new Atleticar(1L), new Trener(1L));
        when(planTreningaRepository.findById(1L)).thenReturn(plan);

        PlanTreningaDto dto = servis.findById(1L);

        assertEquals(3, dto.getIntenzitet());
    }

    @Test
    void testFindByIdBacaIzuzetak() throws NjtException {
        when(planTreningaRepository.findById(9L)).thenThrow(new NjtException("Nije pronađen"));

        assertThrows(NjtException.class, () -> servis.findById(9L));
    }

    @Test
    void testAddPovezujeStavkeSaPlanom() {
        StavkaPlanaDto stavkaDto = new StavkaPlanaDto(null, 1L, 8L, 3, 10, 2);
        PlanTreningaDto dto = new PlanTreningaDto(null, new Date(0), 4, 1L, 1L, List.of(stavkaDto));

        servis.add(dto);

        ArgumentCaptor<PlanTreninga> captor = ArgumentCaptor.forClass(PlanTreninga.class);
        verify(planTreningaRepository, times(1)).save(captor.capture());
        PlanTreninga sacuvan = captor.getValue();

        assertSame(sacuvan, sacuvan.getStavke().get(0).getPlan());
    }

    @Test
    void testAddBezStavki() {
        PlanTreningaDto dto = new PlanTreningaDto(null, new Date(0), 4, 1L, 1L, null);

        PlanTreningaDto rezultat = servis.add(dto);

        verify(planTreningaRepository, times(1)).save(any(PlanTreninga.class));
        assertEquals(4, rezultat.getIntenzitet());
    }

    @Test
    void testDeleteById() {
        servis.deleteById(2L);

        verify(planTreningaRepository, times(1)).deleteById(2L);
    }
}
