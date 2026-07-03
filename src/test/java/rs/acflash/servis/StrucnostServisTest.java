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

import rs.acflash.dto.impl.StrucnostDto;
import rs.acflash.entity.impl.Strucnost;
import rs.acflash.exception.NjtException;
import rs.acflash.mapper.impl.StrucnostMapper;
import rs.acflash.repository.impl.StrucnostRepository;

@ExtendWith(MockitoExtension.class)
class StrucnostServisTest {

    @Mock
    private StrucnostRepository strucnostRepository;

    private StrucnostMapper strucnostMapper;

    private StrucnostServis servis;

    @BeforeEach
    void setUp() {
        strucnostMapper = new StrucnostMapper();
        servis = new StrucnostServis(strucnostRepository, strucnostMapper);
    }

    @Test
    void testFindAll() {
        when(strucnostRepository.findAll()).thenReturn(List.of(new Strucnost(1L, "Kondicija", "Opis", null)));

        List<StrucnostDto> rezultat = servis.findAll();

        assertEquals(1, rezultat.size());
    }

    @Test
    void testFindById() throws NjtException {
        when(strucnostRepository.findById(1L)).thenReturn(new Strucnost(1L, "Kondicija", "Opis", null));

        StrucnostDto dto = servis.findById(1L);

        assertEquals("Kondicija", dto.getNaziv());
    }

    @Test
    void testFindByIdBacaIzuzetak() throws NjtException {
        when(strucnostRepository.findById(5L)).thenThrow(new NjtException("Nije pronađena"));

        assertThrows(NjtException.class, () -> servis.findById(5L));
    }

    @Test
    void testAdd() {
        StrucnostDto dto = new StrucnostDto(null, "Tehnika", "Opis");

        StrucnostDto rezultat = servis.add(dto);

        verify(strucnostRepository, times(1)).save(any(Strucnost.class));
        assertEquals("Tehnika", rezultat.getNaziv());
    }

    @Test
    void testUpdate() {
        StrucnostDto dto = new StrucnostDto(1L, "Tehnika", "Novi opis");

        StrucnostDto rezultat = servis.update(dto);

        verify(strucnostRepository, times(1)).save(any(Strucnost.class));
        assertEquals("Novi opis", rezultat.getOpis());
    }

    @Test
    void testDeleteById() {
        servis.deleteById(3L);

        verify(strucnostRepository, times(1)).deleteById(3L);
    }
}
