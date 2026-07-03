package rs.acflash.servis;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import rs.acflash.dto.impl.AtleticarDto;
import rs.acflash.entity.impl.Atleticar;
import rs.acflash.entity.impl.Kategorija;
import rs.acflash.exception.NjtException;
import rs.acflash.mapper.impl.AtleticarMapper;
import rs.acflash.repository.impl.AtleticarRepository;

@ExtendWith(MockitoExtension.class)
class AtleticarServisTest {

    @Mock
    private AtleticarRepository atleticarRepository;

    private AtleticarMapper atleticarMapper;

    private AtleticarServis servis;

    @BeforeEach
    void setUp() {
        atleticarMapper = new AtleticarMapper();
        servis = new AtleticarServis(atleticarRepository, atleticarMapper);
    }

    @Test
    void testFindAll() {
        Atleticar a = new Atleticar(1L, "Marko", "Markovic", new Date(0), "M", 180.0, 75.0, 23.1, "url", new Kategorija(1L));
        when(atleticarRepository.findAll()).thenReturn(List.of(a));

        assertEquals(1, servis.findAll().size());
    }

    @Test
    void testFindById() throws NjtException {
        Atleticar a = new Atleticar(1L, "Marko", "Markovic", new Date(0), "M", 180.0, 75.0, 23.1, "url", new Kategorija(1L));
        when(atleticarRepository.findById(1L)).thenReturn(a);

        AtleticarDto dto = servis.findById(1L);

        assertEquals("Marko", dto.getIme());
    }

    @Test
    void testFindByIdBacaIzuzetak() throws NjtException {
        when(atleticarRepository.findById(9L)).thenThrow(new NjtException("Nije pronađen"));

        assertThrows(NjtException.class, () -> servis.findById(9L));
    }

    @Test
    void testAdd() {
        AtleticarDto dto = new AtleticarDto(null, "Ana", "Anic", new Date(0), "Z", 170.0, 60.0, 20.7, 2L, "url");

        AtleticarDto rezultat = servis.add(dto);

        verify(atleticarRepository, times(1)).save(any(Atleticar.class));
        assertEquals("Ana", rezultat.getIme());
    }

    @Test
    void testUpdate() {
        AtleticarDto dto = new AtleticarDto(1L, "Ana", "Anic", new Date(0), "Z", 172.0, 61.0, 20.6, 2L, "url");

        AtleticarDto rezultat = servis.update(dto);

        verify(atleticarRepository, times(1)).save(any(Atleticar.class));
        assertEquals(172.0, rezultat.getVisina());
    }

    @Test
    void testDeleteById() {
        servis.deleteById(4L);

        verify(atleticarRepository, times(1)).deleteById(4L);
    }
}
