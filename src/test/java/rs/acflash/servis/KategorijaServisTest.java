package rs.acflash.servis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import rs.acflash.dto.impl.KategorijaDto;
import rs.acflash.entity.impl.Kategorija;
import rs.acflash.exception.NjtException;
import rs.acflash.mapper.impl.KategorijaMapper;
import rs.acflash.repository.impl.KategorijaRepository;

@ExtendWith(MockitoExtension.class)
class KategorijaServisTest {

    @Mock
    private KategorijaRepository kategorijaRepository;

    private KategorijaMapper kategorijaMapper;

    private KategorijaServis servis;

    @BeforeEach
    void setUp() {
        kategorijaMapper = new KategorijaMapper();
        servis = new KategorijaServis(kategorijaRepository, kategorijaMapper);
    }

    @Test
    void testFindAll() {
        Kategorija k = new Kategorija(1L, "Sprint", "Opis", null);
        when(kategorijaRepository.findAll()).thenReturn(List.of(k));

        List<KategorijaDto> rezultat = servis.findAll();

        assertEquals(1, rezultat.size());
        assertEquals("Sprint", rezultat.get(0).getNaziv());
    }

    @Test
    void testFindAllPrazno() {
        when(kategorijaRepository.findAll()).thenReturn(List.of());

        assertEquals(0, servis.findAll().size());
    }

    @Test
    void testFindById() throws NjtException {
        Kategorija k = new Kategorija(1L, "Sprint", "Opis", null);
        when(kategorijaRepository.findById(1L)).thenReturn(k);

        KategorijaDto dto = servis.findById(1L);

        assertEquals("Sprint", dto.getNaziv());
    }

    @Test
    void testFindByIdNepostojeciBacaIzuzetak() throws NjtException {
        when(kategorijaRepository.findById(99L)).thenThrow(new NjtException("Kategorija nije pronađena"));

        org.junit.jupiter.api.Assertions.assertThrows(NjtException.class, () -> servis.findById(99L));
    }

    @Test
    void testAdd() {
        KategorijaDto dto = new KategorijaDto(null, "Bacanja", "Opis");

        KategorijaDto rezultat = servis.add(dto);

        verify(kategorijaRepository, times(1)).save(any(Kategorija.class));
        assertEquals("Bacanja", rezultat.getNaziv());
    }

    @Test
    void testUpdate() {
        KategorijaDto dto = new KategorijaDto(1L, "Bacanja", "Novi opis");

        KategorijaDto rezultat = servis.update(dto);

        verify(kategorijaRepository, times(1)).save(any(Kategorija.class));
        assertEquals("Novi opis", rezultat.getOpis());
    }

    @Test
    void testDeleteById() {
        servis.deleteById(1L);

        verify(kategorijaRepository, times(1)).deleteById(1L);
        verify(kategorijaRepository, never()).deleteById(2L);
    }
}
