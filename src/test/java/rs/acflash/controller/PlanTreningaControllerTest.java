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

import rs.acflash.dto.impl.PlanTreningaDto;
import rs.acflash.exception.NjtException;
import rs.acflash.servis.PlanTreningaServis;

@ExtendWith(MockitoExtension.class)
class PlanTreningaControllerTest {

    @Mock
    private PlanTreningaServis planTreningaServis;

    private PlanTreningaController controller;

    @BeforeEach
    void setUp() {
        controller = new PlanTreningaController(planTreningaServis);
    }

    private PlanTreningaDto dto() {
        return new PlanTreningaDto(1L, new Date(0), 3, 1L, 1L, null);
    }

    @Test
    void testGetAll() {
        when(planTreningaServis.findAll()).thenReturn(List.of(dto()));

        ResponseEntity<List<PlanTreningaDto>> odgovor = controller.getAll();

        assertEquals(HttpStatus.OK, odgovor.getStatusCode());
        assertEquals(1, odgovor.getBody().size());
    }

    @Test
    void testFindByIdUspesno() throws NjtException {
        when(planTreningaServis.findById(1L)).thenReturn(dto());

        ResponseEntity<PlanTreningaDto> odgovor = controller.finById(1L);

        assertEquals(HttpStatus.OK, odgovor.getStatusCode());
    }

    @Test
    void testFindByIdNepostojeci() throws NjtException {
        when(planTreningaServis.findById(99L)).thenThrow(new NjtException("Nije pronađen"));

        assertThrows(ResponseStatusException.class, () -> controller.finById(99L));
    }

    @Test
    void testAddUspesno() {
        PlanTreningaDto noviDto = dto();
        when(planTreningaServis.add(noviDto)).thenReturn(dto());

        ResponseEntity<PlanTreningaDto> odgovor = controller.add(noviDto);

        assertEquals(HttpStatus.CREATED, odgovor.getStatusCode());
    }

    @Test
    void testDeleteUspesno() {
        ResponseEntity<String> odgovor = controller.delete(1L);

        verify(planTreningaServis, times(1)).deleteById(1L);
        assertEquals(HttpStatus.OK, odgovor.getStatusCode());
    }

    @Test
    void testDeleteNepostojeci() {
        doThrow(new RuntimeException("ne postoji")).when(planTreningaServis).deleteById(99L);

        ResponseEntity<String> odgovor = controller.delete(99L);

        assertEquals(HttpStatus.NOT_FOUND, odgovor.getStatusCode());
    }

    @Test
    void testUpdateUspesno() {
        when(planTreningaServis.update(any(PlanTreningaDto.class))).thenReturn(dto());

        ResponseEntity<PlanTreningaDto> odgovor = controller.update(1L, dto());

        assertEquals(HttpStatus.OK, odgovor.getStatusCode());
    }
}
