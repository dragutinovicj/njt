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

import rs.acflash.dto.impl.TrenerStrucnostDto;
import rs.acflash.entity.impl.Strucnost;
import rs.acflash.entity.impl.Trener;
import rs.acflash.entity.impl.TrenerStrucnost;
import rs.acflash.entity.impl.TrenerStrucnostId;
import rs.acflash.exception.NjtException;
import rs.acflash.mapper.impl.TrenerStrucnostMapper;
import rs.acflash.repository.impl.TrenerStrucnostRepository;

@ExtendWith(MockitoExtension.class)
class TrenerStrucnostServisTest {

    @Mock
    private TrenerStrucnostRepository trenerStrucnostRepository;

    private TrenerStrucnostMapper trenerStrucnostMapper;

    private TrenerStrucnostServis servis;

    @BeforeEach
    void setUp() {
        trenerStrucnostMapper = new TrenerStrucnostMapper();
        servis = new TrenerStrucnostServis(trenerStrucnostRepository, trenerStrucnostMapper);
    }

    private TrenerStrucnost odnos() {
        return new TrenerStrucnost(new TrenerStrucnostId(1L, 2L), new Trener(1L), new Strucnost(2L), "Sertifikat");
    }

    @Test
    void testFindAll() {
        when(trenerStrucnostRepository.findAll()).thenReturn(List.of(odnos()));

        assertEquals(1, servis.findAll().size());
    }

    @Test
    void testFindById() throws NjtException {
        TrenerStrucnostId id = new TrenerStrucnostId(1L, 2L);
        when(trenerStrucnostRepository.findById(id)).thenReturn(odnos());

        TrenerStrucnostDto dto = servis.findById(id);

        assertEquals("Sertifikat", dto.getOdlikovanje());
    }

    @Test
    void testFindByIdBacaIzuzetak() throws NjtException {
        TrenerStrucnostId id = new TrenerStrucnostId(9L, 9L);
        when(trenerStrucnostRepository.findById(id)).thenThrow(new NjtException("Nije pronađena"));

        assertThrows(NjtException.class, () -> servis.findById(id));
    }

    @Test
    void testFindByTrenerId() {
        when(trenerStrucnostRepository.findByTrenerId(1L)).thenReturn(List.of(odnos()));

        List<TrenerStrucnostDto> rezultat = servis.findByTrenerId(1L);

        assertEquals(1, rezultat.size());
        assertEquals(1L, rezultat.get(0).getIdTrener());
    }

    @Test
    void testFindByStrucnostId() {
        when(trenerStrucnostRepository.findByStrucnostId(2L)).thenReturn(List.of(odnos()));

        List<TrenerStrucnostDto> rezultat = servis.findByStrucnostId(2L);

        assertEquals(1, rezultat.size());
        assertEquals(2L, rezultat.get(0).getIdStrucnost());
    }

    @Test
    void testAdd() {
        TrenerStrucnostDto dto = new TrenerStrucnostDto(1L, 2L, "Novo odlikovanje");

        TrenerStrucnostDto rezultat = servis.add(dto);

        verify(trenerStrucnostRepository, times(1)).save(any(TrenerStrucnost.class));
        assertEquals("Novo odlikovanje", rezultat.getOdlikovanje());
    }

    @Test
    void testDeleteById() {
        TrenerStrucnostId id = new TrenerStrucnostId(1L, 2L);

        servis.deleteById(id);

        verify(trenerStrucnostRepository, times(1)).deleteById(id);
    }
}
