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
import rs.acflash.entity.impl.Strucnost;
import rs.acflash.exception.NjtException;

@ExtendWith(MockitoExtension.class)
class StrucnostRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Strucnost> typedQuery;

    @InjectMocks
    private StrucnostRepository repository;

    @Test
    void testFindAll() {
        when(entityManager.createQuery(anyString(), eq(Strucnost.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(List.of(new Strucnost(1L, "Kondicija", "Opis", null)));

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void testFindByIdPostoji() throws NjtException {
        when(entityManager.find(Strucnost.class, 1L)).thenReturn(new Strucnost(1L, "Kondicija", "Opis", null));

        assertEquals("Kondicija", repository.findById(1L).getNaziv());
    }

    @Test
    void testFindByIdNePostojiBacaIzuzetak() {
        when(entityManager.find(Strucnost.class, 5L)).thenReturn(null);

        assertThrows(NjtException.class, () -> repository.findById(5L));
    }

    @Test
    void testSaveNoviPozivaPersist() {
        Strucnost s = new Strucnost(null, "Tehnika", "Opis", null);

        repository.save(s);

        verify(entityManager, times(1)).persist(s);
    }

    @Test
    void testSavePostojeciPozivaMerge() {
        Strucnost s = new Strucnost(1L, "Tehnika", "Opis", null);

        repository.save(s);

        verify(entityManager, times(1)).merge(s);
    }

    @Test
    void testDeleteByIdPostojeci() {
        Strucnost s = new Strucnost(1L, "Tehnika", "Opis", null);
        when(entityManager.find(Strucnost.class, 1L)).thenReturn(s);

        repository.deleteById(1L);

        verify(entityManager, times(1)).remove(s);
    }
}
