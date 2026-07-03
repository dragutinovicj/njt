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

import rs.acflash.dto.impl.TrenerDto;
import rs.acflash.exception.NjtException;
import rs.acflash.servis.TrenerServis;

@ExtendWith(MockitoExtension.class)
class TrenerControllerTest {

    @Mock
    private TrenerServis trenerServis;

    private TrenerController controller;

    @BeforeEach
    void setUp() {
        controller = new TrenerController(trenerServis);
    }

    @Test
    void testGetAll() {
        when(trenerServis.findAll()).thenReturn(List.of(new TrenerDto(1L, "Petar", "Petrovic", "petar@fon.rs", "sifra")));

        ResponseEntity<List<TrenerDto>> odgovor = controller.getAll();

        assertEquals(HttpStatus.OK, odgovor.getStatusCode());
        assertEquals(1, odgovor.getBody().size());
    }

    @Test
    void testFindByIdUspesno() throws NjtException {
        when(trenerServis.findById(1L)).thenReturn(new TrenerDto(1L, "Petar", "Petrovic", "petar@fon.rs", "sifra"));

        ResponseEntity<TrenerDto> odgovor = controller.finById(1L);

        assertEquals(HttpStatus.OK, odgovor.getStatusCode());
    }

    @Test
    void testFindByIdNepostojeci() throws NjtException {
        when(trenerServis.findById(99L)).thenThrow(new NjtException("Trener nije pronađen"));

        assertThrows(ResponseStatusException.class, () -> controller.finById(99L));
    }

    @Test
    void testAddUspesno() {
        TrenerDto dto = new TrenerDto(null, "Jovan", "Jovanovic", "jovan@fon.rs", "lozinka1");
        when(trenerServis.add(dto)).thenReturn(new TrenerDto(1L, "Jovan", "Jovanovic", "jovan@fon.rs", "lozinka1"));

        ResponseEntity<TrenerDto> odgovor = controller.add(dto);

        assertEquals(HttpStatus.CREATED, odgovor.getStatusCode());
    }

    @Test
    void testDeleteUspesno() {
        ResponseEntity<String> odgovor = controller.delete(1L);

        verify(trenerServis, times(1)).deleteById(1L);
        assertEquals(HttpStatus.OK, odgovor.getStatusCode());
    }

    @Test
    void testDeleteNepostojeci() {
        doThrow(new RuntimeException("ne postoji")).when(trenerServis).deleteById(99L);

        ResponseEntity<String> odgovor = controller.delete(99L);

        assertEquals(HttpStatus.NOT_FOUND, odgovor.getStatusCode());
    }

    @Test
    void testUpdateUspesno() {
        when(trenerServis.update(any(TrenerDto.class)))
                .thenReturn(new TrenerDto(1L, "Jovan", "Jovanovic", "jovan@fon.rs", "novalozinka"));

        ResponseEntity<TrenerDto> odgovor = controller.update(1L, new TrenerDto(null, "Jovan", "Jovanovic", "jovan@fon.rs", "novalozinka"));

        assertEquals(HttpStatus.OK, odgovor.getStatusCode());
    }
}
