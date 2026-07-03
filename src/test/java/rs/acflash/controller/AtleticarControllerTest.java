package rs.acflash.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import rs.acflash.dto.impl.AtleticarDto;
import rs.acflash.exception.NjtException;
import rs.acflash.servis.AtleticarServis;

@ExtendWith(MockitoExtension.class)
class AtleticarControllerTest {

    @Mock
    private AtleticarServis atleticarServis;

    private AtleticarController controller;

    @BeforeEach
    void setUp() {
        controller = new AtleticarController(atleticarServis);
    }

    private AtleticarDto dto() {
        return new AtleticarDto(1L, "Marko", "Markovic", new Date(0), "M", 180.0, 75.0, 23.1, 1L, "url");
    }

    @Test
    void testGetAll() {
        when(atleticarServis.findAll()).thenReturn(List.of(dto()));

        ResponseEntity<List<AtleticarDto>> odgovor = controller.getAll();

        assertEquals(HttpStatus.OK, odgovor.getStatusCode());
        assertEquals(1, odgovor.getBody().size());
    }

    @Test
    void testFindByIdUspesno() throws NjtException {
        when(atleticarServis.findById(1L)).thenReturn(dto());

        ResponseEntity<AtleticarDto> odgovor = controller.finById(1L);

        assertEquals(HttpStatus.OK, odgovor.getStatusCode());
    }

    @Test
    void testFindByIdNepostojeci() throws NjtException {
        when(atleticarServis.findById(99L)).thenThrow(new NjtException("Nije pronađen"));

        assertThrows(ResponseStatusException.class, () -> controller.finById(99L));
    }

    @Test
    void testAddUspesno() {
        AtleticarDto noviDto = dto();
        when(atleticarServis.add(noviDto)).thenReturn(dto());

        ResponseEntity<AtleticarDto> odgovor = controller.add(noviDto);

        assertEquals(HttpStatus.CREATED, odgovor.getStatusCode());
    }

    @Test
    void testDeleteUspesno() {
        ResponseEntity<String> odgovor = controller.delete(1L);

        verify(atleticarServis, times(1)).deleteById(1L);
        assertEquals(HttpStatus.OK, odgovor.getStatusCode());
    }

    @Test
    void testDeleteNepostojeci() {
        doThrow(new RuntimeException("ne postoji")).when(atleticarServis).deleteById(99L);

        ResponseEntity<String> odgovor = controller.delete(99L);

        assertEquals(HttpStatus.NOT_FOUND, odgovor.getStatusCode());
    }

    @Test
    void testUpdateUspesno() {
        when(atleticarServis.update(any(AtleticarDto.class))).thenReturn(dto());

        ResponseEntity<AtleticarDto> odgovor = controller.update(1L, dto());

        assertEquals(HttpStatus.OK, odgovor.getStatusCode());
    }
}
