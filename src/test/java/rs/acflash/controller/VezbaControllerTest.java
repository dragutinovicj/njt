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

import rs.acflash.dto.impl.VezbaDto;
import rs.acflash.exception.NjtException;
import rs.acflash.servis.VezbaServis;

@ExtendWith(MockitoExtension.class)
class VezbaControllerTest {

    @Mock
    private VezbaServis vezbaServis;

    private VezbaController controller;

    @BeforeEach
    void setUp() {
        controller = new VezbaController(vezbaServis);
    }

    @Test
    void testGetAll() {
        when(vezbaServis.findAll()).thenReturn(List.of(new VezbaDto(1L, "Cucanj", "Snaga", "Opis", 5)));

        ResponseEntity<List<VezbaDto>> odgovor = controller.getAll();

        assertEquals(HttpStatus.OK, odgovor.getStatusCode());
        assertEquals(1, odgovor.getBody().size());
    }

    @Test
    void testFindByIdUspesno() throws NjtException {
        when(vezbaServis.findById(1L)).thenReturn(new VezbaDto(1L, "Cucanj", "Snaga", "Opis", 5));

        ResponseEntity<VezbaDto> odgovor = controller.finById(1L);

        assertEquals(HttpStatus.OK, odgovor.getStatusCode());
    }

    @Test
    void testFindByIdNepostojeci() throws NjtException {
        when(vezbaServis.findById(99L)).thenThrow(new NjtException("Nije pronađena"));

        assertThrows(ResponseStatusException.class, () -> controller.finById(99L));
    }

    @Test
    void testAddUspesno() {
        VezbaDto dto = new VezbaDto(null, "Skip A", "Tehnika", "Opis", 3);
        when(vezbaServis.add(dto)).thenReturn(new VezbaDto(1L, "Skip A", "Tehnika", "Opis", 3));

        ResponseEntity<VezbaDto> odgovor = controller.add(dto);

        assertEquals(HttpStatus.CREATED, odgovor.getStatusCode());
    }

    @Test
    void testDeleteUspesno() {
        ResponseEntity<String> odgovor = controller.delete(1L);

        verify(vezbaServis, times(1)).deleteById(1L);
        assertEquals(HttpStatus.OK, odgovor.getStatusCode());
    }

    @Test
    void testDeleteNepostojeci() {
        doThrow(new RuntimeException("ne postoji")).when(vezbaServis).deleteById(99L);

        ResponseEntity<String> odgovor = controller.delete(99L);

        assertEquals(HttpStatus.NOT_FOUND, odgovor.getStatusCode());
    }

    @Test
    void testUpdateUspesno() {
        when(vezbaServis.update(any(VezbaDto.class))).thenReturn(new VezbaDto(1L, "Skip A", "Tehnika", "Opis", 4));

        ResponseEntity<VezbaDto> odgovor = controller.update(1L, new VezbaDto(null, "Skip A", "Tehnika", "Opis", 4));

        assertEquals(HttpStatus.OK, odgovor.getStatusCode());
    }
}
