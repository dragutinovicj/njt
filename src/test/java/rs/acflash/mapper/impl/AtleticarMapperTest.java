package rs.acflash.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rs.acflash.dto.impl.AtleticarDto;
import rs.acflash.entity.impl.Atleticar;
import rs.acflash.entity.impl.Kategorija;

class AtleticarMapperTest {

    private AtleticarMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new AtleticarMapper();
    }

    @Test
    void testToDtoSaKategorijom() {
        Kategorija kategorija = new Kategorija(3L, "Sprint", "Opis", null);
        Date datum = new Date(0);
        Atleticar a = new Atleticar(1L, "Marko", "Markovic", datum, "M", 180.0, 75.0, 23.1, "http://slika.jpg", kategorija);

        AtleticarDto dto = mapper.toDto(a);

        assertEquals(1L, dto.getIdAtleticar());
        assertEquals("Marko", dto.getIme());
        assertEquals("Markovic", dto.getPrezime());
        assertEquals(datum, dto.getDatumRodjenja());
        assertEquals("M", dto.getPol());
        assertEquals(180.0, dto.getVisina());
        assertEquals(75.0, dto.getTezina());
        assertEquals(23.1, dto.getBmi());
        assertEquals("http://slika.jpg", dto.getImageUrl());
        assertEquals(3L, dto.getIdKategorija());
    }

    @Test
    void testToDtoBezKategorije() {
        Atleticar a = new Atleticar(1L, "Marko", "Markovic", new Date(0), "M", 180.0, 75.0, 23.1, null, null);

        AtleticarDto dto = mapper.toDto(a);

        assertNull(dto.getIdKategorija());
    }

    @Test
    void testToEntitySaKategorijom() {
        AtleticarDto dto = new AtleticarDto(1L, "Ana", "Anic", new Date(0), "Z", 170.0, 60.0, 20.7, 5L, "http://slika.jpg");

        Atleticar a = mapper.toEntity(dto);

        assertEquals("Ana", a.getIme());
        assertEquals("Anic", a.getPrezime());
        assertEquals(5L, a.getKategorija().getIdKategorija());
    }

    @Test
    void testToEntityBezKategorije() {
        AtleticarDto dto = new AtleticarDto(1L, "Ana", "Anic", new Date(0), "Z", 170.0, 60.0, 20.7, null, null);

        Atleticar a = mapper.toEntity(dto);

        assertNull(a.getKategorija());
    }
}
