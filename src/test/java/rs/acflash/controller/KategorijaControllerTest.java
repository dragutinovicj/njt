package rs.acflash.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import rs.acflash.dto.impl.KategorijaDto;
import rs.acflash.exception.NjtException;
import rs.acflash.servis.KategorijaServis;

@ExtendWith(MockitoExtension.class)
class KategorijaControllerTest {

    @Mock
    private KategorijaServis kategorijaServis;

    private KategorijaController controller;

    @BeforeEach
    void setUp() {
        controller = new KategorijaController(kategorijaServis);
    }

    @Test
    void testGetAll() {
        when(kategorijaServis.findAll()).thenReturn(List.of(new KategorijaDto(1L, "Sprint", "Opis")));

        ResponseEntity<List<KategorijaDto>> odgovor = controller.getAll();

        assertEquals(HttpStatus.OK, odgovor.getStatusCode());
        assertEquals(1, odgovor.getBody().size());
    }

    @Test
    void testFindByIdUspesno() throws NjtException {
        when(kategorijaServis.findById(1L)).thenReturn(new KategorijaDto(1L, "Sprint", "Opis"));

        ResponseEntity<KategorijaDto> odgovor = controller.finById(1L);

        assertEquals(HttpStatus.OK, odgovor.getStatusCode());
        assertEquals("Sprint", odgovor.getBody().getNaziv());
    }

    @Test
    void testFindByIdNepostojeciBacaResponseStatusException() throws NjtException {
        when(kategorijaServis.findById(99L)).thenThrow(new NjtException("Nije pronađena"));

        assertEquals(HttpStatus.BAD_REQUEST,
                assertThrowsResponseStatus(() -> controller.finById(99L)).getStatusCode());
    }

    @Test
    void testAddUspesno() {
        KategorijaDto dto = new KategorijaDto(null, "Bacanja", "Opis");
        when(kategorijaServis.add(dto)).thenReturn(new KategorijaDto(1L, "Bacanja", "Opis"));

        ResponseEntity<KategorijaDto> odgovor = controller.add(dto);

        assertEquals(HttpStatus.CREATED, odgovor.getStatusCode());
        assertEquals(1L, odgovor.getBody().getIdKategorija());
    }

    @Test
    void testDeleteUspesno() {
        ResponseEntity<String> odgovor = controller.delete(1L);

        verify(kategorijaServis, times(1)).deleteById(1L);
        assertEquals(HttpStatus.OK, odgovor.getStatusCode());
    }

    @Test
    void testDeleteNepostojeci() {
        org.mockito.Mockito.doThrow(new RuntimeException("ne postoji")).when(kategorijaServis).deleteById(99L);

        ResponseEntity<String> odgovor = controller.delete(99L);

        assertEquals(HttpStatus.NOT_FOUND, odgovor.getStatusCode());
    }

    @Test
    void testUpdateUspesno() {
        KategorijaDto dto = new KategorijaDto(null, "Bacanja", "Novi opis");
        when(kategorijaServis.update(any(KategorijaDto.class))).thenReturn(new KategorijaDto(1L, "Bacanja", "Novi opis"));

        ResponseEntity<KategorijaDto> odgovor = controller.update(1L, dto);

        assertEquals(HttpStatus.OK, odgovor.getStatusCode());
        assertEquals(1L, dto.getIdKategorija());
    }

    private ResponseStatusException assertThrowsResponseStatus(Runnable runnable) {
        return org.junit.jupiter.api.Assertions.assertThrows(ResponseStatusException.class, runnable::run);
    }
}
