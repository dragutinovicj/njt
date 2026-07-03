package rs.acflash.repository.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import rs.acflash.entity.impl.Kategorija;
import rs.acflash.exception.NjtException;

@ExtendWith(MockitoExtension.class)
class KategorijaRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Kategorija> typedQuery;

    @InjectMocks
    private KategorijaRepository repository;

    @Test
    void testFindAll() {
        when(entityManager.createQuery(anyString(), org.mockito.ArgumentMatchers.eq(Kategorija.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(List.of(new Kategorija(1L, "Sprint", "Opis", null)));

        List<Kategorija> rezultat = repository.findAll();

        assertEquals(1, rezultat.size());
    }

    @Test
    void testFindByIdPostoji() throws NjtException {
        Kategorija k = new Kategorija(1L, "Sprint", "Opis", null);
        when(entityManager.find(Kategorija.class, 1L)).thenReturn(k);

        Kategorija rezultat = repository.findById(1L);

        assertEquals("Sprint", rezultat.getNaziv());
    }

    @Test
    void testFindByIdNePostojiBacaIzuzetak() {
        when(entityManager.find(Kategorija.class, 99L)).thenReturn(null);

        assertThrows(NjtException.class, () -> repository.findById(99L));
    }

    @Test
    void testSaveNoviObjekatPozivaPersist() {
        Kategorija k = new Kategorija(null, "Skokovi", "Opis", null);

        repository.save(k);

        verify(entityManager, times(1)).persist(k);
    }

    @Test
    void testSavePostojeciObjekatPozivaMerge() {
        Kategorija k = new Kategorija(1L, "Skokovi", "Opis", null);

        repository.save(k);

        verify(entityManager, times(1)).merge(k);
    }

    @Test
    void testDeleteByIdPostojeciBrise() {
        Kategorija k = new Kategorija(1L, "Skokovi", "Opis", null);
        when(entityManager.find(Kategorija.class, 1L)).thenReturn(k);

        repository.deleteById(1L);

        verify(entityManager, times(1)).remove(k);
    }

    @Test
    void testDeleteByIdNepostojeciNeRadiNista() {
        when(entityManager.find(Kategorija.class, 99L)).thenReturn(null);

        repository.deleteById(99L);

        verify(entityManager, org.mockito.Mockito.never()).remove(any());
    }
}
