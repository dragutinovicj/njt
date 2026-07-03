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

import rs.acflash.dto.impl.TrenerDto;
import rs.acflash.entity.impl.Trener;
import rs.acflash.exception.NjtException;
import rs.acflash.mapper.impl.TrenerMapper;
import rs.acflash.repository.impl.TrenerRepository;

@ExtendWith(MockitoExtension.class)
class TrenerServisTest {

    @Mock
    private TrenerRepository trenerRepository;

    private TrenerMapper trenerMapper;

    private TrenerServis servis;

    @BeforeEach
    void setUp() {
        trenerMapper = new TrenerMapper();
        servis = new TrenerServis(trenerRepository, trenerMapper);
    }

    @Test
    void testFindAll() {
        when(trenerRepository.findAll()).thenReturn(List.of(new Trener(1L, "Petar", "Petrovic", "petar@fon.rs", "sifra", null)));

        assertEquals(1, servis.findAll().size());
    }

    @Test
    void testFindById() throws NjtException {
        when(trenerRepository.findById(1L)).thenReturn(new Trener(1L, "Petar", "Petrovic", "petar@fon.rs", "sifra", null));

        TrenerDto dto = servis.findById(1L);

        assertEquals("Petar", dto.getIme());
    }

    @Test
    void testFindByIdBacaIzuzetak() throws NjtException {
        when(trenerRepository.findById(9L)).thenThrow(new NjtException("Trener nije pronađen"));

        assertThrows(NjtException.class, () -> servis.findById(9L));
    }

    @Test
    void testAdd() {
        TrenerDto dto = new TrenerDto(null, "Jovan", "Jovanovic", "jovan@fon.rs", "lozinka1");

        TrenerDto rezultat = servis.add(dto);

        verify(trenerRepository, times(1)).save(any(Trener.class));
        assertEquals("Jovan", rezultat.getIme());
    }

    @Test
    void testUpdate() {
        TrenerDto dto = new TrenerDto(1L, "Jovan", "Jovanovic", "jovan@fon.rs", "novalozinka");

        TrenerDto rezultat = servis.update(dto);

        verify(trenerRepository, times(1)).save(any(Trener.class));
        assertEquals("novalozinka", rezultat.getLozinka());
    }

    @Test
    void testDeleteById() {
        servis.deleteById(3L);

        verify(trenerRepository, times(1)).deleteById(3L);
    }
}
