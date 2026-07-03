package rs.acflash.repository.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import rs.acflash.entity.impl.Trener;
import rs.acflash.exception.NjtException;

@ExtendWith(MockitoExtension.class)
class TrenerRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Trener> trenerQuery;

    @Mock
    private TypedQuery<Long> longQuery;

    @InjectMocks
    private TrenerRepository repository;

    private Trener trener(Long id) {
        return new Trener(id, "Petar", "Petrovic", "petar@fon.rs", "sifra", null);
    }

    @Test
    void testFindAll() {
        when(entityManager.createQuery(anyString(), eq(Trener.class))).thenReturn(trenerQuery);
        when(trenerQuery.getResultList()).thenReturn(List.of(trener(1L)));

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void testFindByIdPostoji() throws NjtException {
        when(entityManager.find(Trener.class, 1L)).thenReturn(trener(1L));

        assertEquals("Petar", repository.findById(1L).getIme());
    }

    @Test
    void testFindByIdNePostojiBacaIzuzetak() {
        when(entityManager.find(Trener.class, 9L)).thenReturn(null);

        assertThrows(NjtException.class, () -> repository.findById(9L));
    }

    @Test
    void testSaveNoviPozivaPersist() {
        Trener t = trener(null);

        repository.save(t);

        verify(entityManager, times(1)).persist(t);
    }

    @Test
    void testSavePostojeciPozivaMerge() {
        Trener t = trener(1L);

        repository.save(t);

        verify(entityManager, times(1)).merge(t);
    }

    @Test
    void testDeleteByIdPostojeci() {
        Trener t = trener(1L);
        when(entityManager.find(Trener.class, 1L)).thenReturn(t);

        repository.deleteById(1L);

        verify(entityManager, times(1)).remove(t);
    }

    @Test
    void testFindByUsernamePostoji() {
        when(entityManager.createQuery(anyString(), eq(Trener.class))).thenReturn(trenerQuery);
        when(trenerQuery.setParameter(eq("username"), eq("petar@fon.rs"))).thenReturn(trenerQuery);
        when(trenerQuery.getSingleResult()).thenReturn(trener(1L));

        Object rezultat = repository.findByUsername("petar@fon.rs");

        assertTrue(rezultat instanceof Trener);
        assertEquals("petar@fon.rs", ((Trener) rezultat).getKorisnickoIme());
    }

    @Test
    void testFindByUsernameNePostojiVracaNull() {
        when(entityManager.createQuery(anyString(), eq(Trener.class))).thenReturn(trenerQuery);
        when(trenerQuery.setParameter(eq("username"), anyString())).thenReturn(trenerQuery);
        when(trenerQuery.getSingleResult()).thenThrow(new NoResultException());

        assertNull(repository.findByUsername("nepostojeci"));
    }

    @Test
    void testExistsByUsernameTrue() {
        when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(longQuery);
        when(longQuery.setParameter(eq("username"), anyString())).thenReturn(longQuery);
        when(longQuery.getSingleResult()).thenReturn(1L);

        assertTrue(repository.existsByUsername("petar@fon.rs"));
    }

    @Test
    void testExistsByUsernameFalse() {
        when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(longQuery);
        when(longQuery.setParameter(eq("username"), anyString())).thenReturn(longQuery);
        when(longQuery.getSingleResult()).thenReturn(0L);

        assertFalse(repository.existsByUsername("nepostojeci"));
    }
}
