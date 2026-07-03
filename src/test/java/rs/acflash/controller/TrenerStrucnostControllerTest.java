package rs.acflash.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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

import rs.acflash.dto.impl.TrenerStrucnostDto;
import rs.acflash.entity.impl.TrenerStrucnostId;
import rs.acflash.exception.NjtException;
import rs.acflash.servis.TrenerStrucnostServis;

@ExtendWith(MockitoExtension.class)
class TrenerStrucnostControllerTest {

    @Mock
    private TrenerStrucnostServis trenerStrucnostServis;

    private TrenerStrucnostController controller;

    @BeforeEach
    void setUp() {
        controller = new TrenerStrucnostController(trenerStrucnostServis);
    }

    private TrenerStrucnostDto dto() {
        return new TrenerStrucnostDto(1L, 2L, "Sertifikat");
    }

    @Test
    void testGetAll() {
        when(trenerStrucnostServis.findAll()).thenReturn(List.of(dto()));

        ResponseEntity<List<TrenerStrucnostDto>> odgovor = controller.getAll();

        assertEquals(HttpStatus.OK, odgovor.getStatusCode());
        assertEquals(1, odgovor.getBody().size());
    }

    @Test
    void testGetByTrenerId() {
        when(trenerStrucnostServis.findByTrenerId(1L)).thenReturn(List.of(dto()));

        ResponseEntity<List<TrenerStrucnostDto>> odgovor = controller.getByTrenerId(1L);

        assertEquals(HttpStatus.OK, odgovor.getStatusCode());
        assertEquals(1, odgovor.getBody().size());
    }

    @Test
    void testGetByStrucnostId() {
        when(trenerStrucnostServis.findByStrucnostId(2L)).thenReturn(List.of(dto()));

        ResponseEntity<List<TrenerStrucnostDto>> odgovor = controller.getByStrucnostId(2L);

        assertEquals(HttpStatus.OK, odgovor.getStatusCode());
        assertEquals(1, odgovor.getBody().size());
    }

    @Test
    void testGetSpecificRelationPostoji() throws NjtException {
        when(trenerStrucnostServis.findById(any(TrenerStrucnostId.class))).thenReturn(dto());

        ResponseEntity<TrenerStrucnostDto> odgovor = controller.getSpecificRelation(1L, 2L);

        assertEquals(HttpStatus.OK, odgovor.getStatusCode());
        assertEquals("Sertifikat", odgovor.getBody().getOdlikovanje());
    }

    @Test
    void testGetSpecificRelationNePostoji() throws NjtException {
        when(trenerStrucnostServis.findById(any(TrenerStrucnostId.class))).thenThrow(new NjtException("Nije pronađena"));

        ResponseEntity<TrenerStrucnostDto> odgovor = controller.getSpecificRelation(1L, 2L);

        assertEquals(HttpStatus.NOT_FOUND, odgovor.getStatusCode());
        assertNull(odgovor.getBody());
    }

    @Test
    void testAddUspesno() {
        TrenerStrucnostDto noviDto = dto();
        when(trenerStrucnostServis.add(noviDto)).thenReturn(dto());

        ResponseEntity<TrenerStrucnostDto> odgovor = controller.add(noviDto);

        assertEquals(HttpStatus.CREATED, odgovor.getStatusCode());
        assertEquals("Sertifikat", odgovor.getBody().getOdlikovanje());
    }

    @Test
    void testDeleteUspesno() {
        ResponseEntity<String> odgovor = controller.delete(1L, 2L);

        verify(trenerStrucnostServis, times(1)).deleteById(any(TrenerStrucnostId.class));
        assertEquals(HttpStatus.OK, odgovor.getStatusCode());
    }

    @Test
    void testDeleteNepostojeci() {
        org.mockito.Mockito.doThrow(new RuntimeException("ne postoji"))
                .when(trenerStrucnostServis).deleteById(any(TrenerStrucnostId.class));

        ResponseEntity<String> odgovor = controller.delete(1L, 2L);

        assertEquals(HttpStatus.NOT_FOUND, odgovor.getStatusCode());
    }

    @Test
    void testUpdateUspesnoPostavljaIdIzPutanje() {
        TrenerStrucnostDto ulazniDto = new TrenerStrucnostDto(null, null, "Novo odlikovanje");
        when(trenerStrucnostServis.update(any(TrenerStrucnostDto.class))).thenReturn(dto());

        ResponseEntity<TrenerStrucnostDto> odgovor = controller.update(1L, 2L, ulazniDto);

        assertEquals(HttpStatus.OK, odgovor.getStatusCode());
        assertEquals(1L, ulazniDto.getIdTrener());
        assertEquals(2L, ulazniDto.getIdStrucnost());
    }
}
