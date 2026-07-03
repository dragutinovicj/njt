package rs.acflash.servis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import rs.acflash.dto.impl.StavkaPlanaDto;
import rs.acflash.entity.impl.PlanTreninga;
import rs.acflash.entity.impl.StavkaPlana;
import rs.acflash.entity.impl.StavkaPlanaId;
import rs.acflash.entity.impl.Vezba;
import rs.acflash.exception.NjtException;
import rs.acflash.mapper.impl.StavkaPlanaMapper;
import rs.acflash.repository.impl.StavkaPlanaRepository;

@ExtendWith(MockitoExtension.class)
class StavkaPlanaServisTest {

    @Mock
    private StavkaPlanaRepository stavkaPlanaRepository;

    private StavkaPlanaMapper stavkaPlanaMapper;

    private StavkaPlanaServis servis;

    @BeforeEach
    void setUp() {
        stavkaPlanaMapper = new StavkaPlanaMapper();
        servis = new StavkaPlanaServis(stavkaPlanaRepository, stavkaPlanaMapper);
    }

    private StavkaPlana stavka() {
        return new StavkaPlana(new StavkaPlanaId(1L, 10L), new PlanTreninga(10L), new Vezba(4L), 3, 12, 5);
    }

    @Test
    void testFindAll() {
        when(stavkaPlanaRepository.findAll()).thenReturn(List.of(stavka()));

        assertEquals(1, servis.findAll().size());
    }

    @Test
    void testFindById() throws NjtException {
        StavkaPlanaId id = new StavkaPlanaId(1L, 10L);
        when(stavkaPlanaRepository.findById(id)).thenReturn(stavka());

        StavkaPlanaDto dto = servis.findById(id);

        assertEquals(3, dto.getBrojSerija());
    }

    @Test
    void testFindByIdBacaIzuzetak() throws NjtException {
        StavkaPlanaId id = new StavkaPlanaId(2L, 20L);
        when(stavkaPlanaRepository.findById(id)).thenThrow(new NjtException("Nije pronađena"));

        assertThrows(NjtException.class, () -> servis.findById(id));
    }

    @Test
    void testAdd() {
        StavkaPlanaDto dto = new StavkaPlanaDto(10L, 1L, 4L, 3, 12, 5);

        StavkaPlanaDto rezultat = servis.add(dto);

        verify(stavkaPlanaRepository, times(1)).save(any(StavkaPlana.class));
        assertEquals(12, rezultat.getBrojPonavljanja());
    }

    @Test
    void testDeleteById() {
        StavkaPlanaId id = new StavkaPlanaId(1L, 10L);

        servis.deleteById(id);

        verify(stavkaPlanaRepository, times(1)).deleteById(id);
    }
}
