package rs.acflash.repository.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import rs.acflash.entity.impl.Vezba;
import rs.acflash.exception.NjtException;

@ExtendWith(MockitoExtension.class)
class VezbaRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Vezba> typedQuery;

    @InjectMocks
    private VezbaRepository repository;

    @Test
    void testFindAll() {
        when(entityManager.createQuery(anyString(), eq(Vezba.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(List.of(new Vezba(1L, "Cucanj", "Snaga", "Opis", 5)));

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void testFindByIdPostoji() throws NjtException {
        when(entityManager.find(Vezba.class, 1L)).thenReturn(new Vezba(1L, "Cucanj", "Snaga", "Opis", 5));

        assertEquals("Cucanj", repository.findById(1L).getNaziv());
    }

    @Test
    void testFindByIdNePostojiBacaIzuzetak() {
        when(entityManager.find(Vezba.class, 9L)).thenReturn(null);

        assertThrows(NjtException.class, () -> repository.findById(9L));
    }

    @Test
    void testSaveNoviPozivaPersist() {
        Vezba v = new Vezba(null, "Skip A", "Tehnika", "Opis", 3);

        repository.save(v);

        verify(entityManager, times(1)).persist(v);
    }

    @Test
    void testSavePostojeciPozivaMerge() {
        Vezba v = new Vezba(1L, "Skip A", "Tehnika", "Opis", 3);

        repository.save(v);

        verify(entityManager, times(1)).merge(v);
    }

    @Test
    void testDeleteByIdPostojeci() {
        Vezba v = new Vezba(1L, "Skip A", "Tehnika", "Opis", 3);
        when(entityManager.find(Vezba.class, 1L)).thenReturn(v);

        repository.deleteById(1L);

        verify(entityManager, times(1)).remove(v);
    }
}
