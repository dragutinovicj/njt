package rs.acflash.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import rs.acflash.dto.impl.StrucnostDto;
import rs.acflash.exception.NjtException;
import rs.acflash.servis.StrucnostServis;

@ExtendWith(MockitoExtension.class)
class StrucnostControllerTest {

    @Mock
    private StrucnostServis strucnostServis;

    private StrucnostController controller;

    @BeforeEach
    void setUp() {
        controller = new StrucnostController(strucnostServis);
    }

    @Test
    void testGetAll() {
        when(strucnostServis.findAll()).thenReturn(List.of(new StrucnostDto(1L, "Kondicija", "Opis")));

        ResponseEntity<List<StrucnostDto>> odgovor = controller.getAll();

        assertEquals(HttpStatus.OK, odgovor.getStatusCode());
        assertEquals(1, odgovor.getBody().size());
    }

    @Test
    void testFindByIdUspesno() throws NjtException {
        when(strucnostServis.findById(1L)).thenReturn(new StrucnostDto(1L, "Kondicija", "Opis"));

        ResponseEntity<StrucnostDto> odgovor = controller.finById(1L);

        assertEquals(HttpStatus.OK, odgovor.getStatusCode());
    }

    @Test
    void testFindByIdNepostojeci() throws NjtException {
        when(strucnostServis.findById(99L)).thenThrow(new NjtException("Nije pronađena"));

        assertThrows(ResponseStatusException.class, () -> controller.finById(99L));
    }

    @Test
    void testAddUspesno() {
        StrucnostDto dto = new StrucnostDto(null, "Tehnika", "Opis");
        when(strucnostServis.add(dto)).thenReturn(new StrucnostDto(1L, "Tehnika", "Opis"));

        ResponseEntity<StrucnostDto> odgovor = controller.add(dto);

        assertEquals(HttpStatus.CREATED, odgovor.getStatusCode());
    }

    @Test
    void testDeleteUspesno() {
        ResponseEntity<String> odgovor = controller.delete(1L);

        verify(strucnostServis, times(1)).deleteById(1L);
        assertEquals(HttpStatus.OK, odgovor.getStatusCode());
    }

    @Test
    void testDeleteNepostojeci() {
        doThrow(new RuntimeException("ne postoji")).when(strucnostServis).deleteById(99L);

        ResponseEntity<String> odgovor = controller.delete(99L);

        assertEquals(HttpStatus.NOT_FOUND, odgovor.getStatusCode());
    }

    @Test
    void testUpdateUspesno() {
        when(strucnostServis.update(any(StrucnostDto.class))).thenReturn(new StrucnostDto(1L, "Tehnika", "Novi opis"));

        ResponseEntity<StrucnostDto> odgovor = controller.update(1L, new StrucnostDto(null, "Tehnika", "Novi opis"));

        assertEquals(HttpStatus.OK, odgovor.getStatusCode());
    }
}
