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

import rs.acflash.dto.impl.VezbaDto;
import rs.acflash.entity.impl.Vezba;
import rs.acflash.exception.NjtException;
import rs.acflash.mapper.impl.VezbaMapper;
import rs.acflash.repository.impl.VezbaRepository;

@ExtendWith(MockitoExtension.class)
class VezbaServisTest {

    @Mock
    private VezbaRepository vezbaRepository;

    private VezbaMapper vezbaMapper;

    private VezbaServis servis;

    @BeforeEach
    void setUp() {
        vezbaMapper = new VezbaMapper();
        servis = new VezbaServis(vezbaRepository, vezbaMapper);
    }

    @Test
    void testFindAll() {
        when(vezbaRepository.findAll()).thenReturn(List.of(new Vezba(1L, "Cucanj", "Snaga", "Opis", 5)));

        assertEquals(1, servis.findAll().size());
    }

    @Test
    void testFindById() throws NjtException {
        when(vezbaRepository.findById(1L)).thenReturn(new Vezba(1L, "Cucanj", "Snaga", "Opis", 5));

        VezbaDto dto = servis.findById(1L);

        assertEquals("Cucanj", dto.getNaziv());
    }

    @Test
    void testFindByIdBacaIzuzetak() throws NjtException {
        when(vezbaRepository.findById(9L)).thenThrow(new NjtException("Nije pronađena"));

        assertThrows(NjtException.class, () -> servis.findById(9L));
    }

    @Test
    void testAdd() {
        VezbaDto dto = new VezbaDto(null, "Skip A", "Tehnika", "Opis", 3);

        VezbaDto rezultat = servis.add(dto);

        verify(vezbaRepository, times(1)).save(any(Vezba.class));
        assertEquals("Skip A", rezultat.getNaziv());
    }

    @Test
    void testUpdate() {
        VezbaDto dto = new VezbaDto(1L, "Skip A", "Tehnika", "Novi opis", 4);

        VezbaDto rezultat = servis.update(dto);

        verify(vezbaRepository, times(1)).save(any(Vezba.class));
        assertEquals(4, rezultat.getTezina());
    }

    @Test
    void testDeleteById() {
        servis.deleteById(2L);

        verify(vezbaRepository, times(1)).deleteById(2L);
    }
}
