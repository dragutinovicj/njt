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
import rs.acflash.entity.impl.Trener;
import rs.acflash.entity.impl.TrenerStrucnost;
import rs.acflash.entity.impl.TrenerStrucnostId;
import rs.acflash.exception.NjtException;

@ExtendWith(MockitoExtension.class)
class TrenerStrucnostRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<TrenerStrucnost> typedQuery;

    @InjectMocks
    private TrenerStrucnostRepository repository;

    private TrenerStrucnost odnos(TrenerStrucnostId id) {
        return new TrenerStrucnost(id, new Trener(1L), new Strucnost(2L), "Sertifikat");
    }

    @Test
    void testFindAll() {
        when(entityManager.createQuery(anyString(), eq(TrenerStrucnost.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(List.of(odnos(new TrenerStrucnostId(1L, 2L))));

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void testFindByIdPostoji() throws NjtException {
        TrenerStrucnostId id = new TrenerStrucnostId(1L, 2L);
        when(entityManager.find(TrenerStrucnost.class, id)).thenReturn(odnos(id));

        assertEquals("Sertifikat", repository.findById(id).getOdlikovanje());
    }

    @Test
    void testFindByIdNePostojiBacaIzuzetak() {
        TrenerStrucnostId id = new TrenerStrucnostId(9L, 9L);
        when(entityManager.find(TrenerStrucnost.class, id)).thenReturn(null);

        assertThrows(NjtException.class, () -> repository.findById(id));
    }

    @Test
    void testSaveNoviPozivaPersist() {
        TrenerStrucnost ts = odnos(null);

        repository.save(ts);

        verify(entityManager, times(1)).persist(ts);
    }

    @Test
    void testSavePostojeciPozivaMerge() {
        TrenerStrucnost ts = odnos(new TrenerStrucnostId(1L, 2L));

        repository.save(ts);

        verify(entityManager, times(1)).merge(ts);
    }

    @Test
    void testDeleteByIdPostojeci() {
        TrenerStrucnostId id = new TrenerStrucnostId(1L, 2L);
        TrenerStrucnost ts = odnos(id);
        when(entityManager.find(TrenerStrucnost.class, id)).thenReturn(ts);

        repository.deleteById(id);

        verify(entityManager, times(1)).remove(ts);
    }

    @Test
    void testFindByStrucnostId() {
        when(entityManager.createQuery(anyString(), eq(TrenerStrucnost.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("idStrucnost"), eq(2L))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(List.of(odnos(new TrenerStrucnostId(1L, 2L))));

        List<TrenerStrucnost> rezultat = repository.findByStrucnostId(2L);

        assertEquals(1, rezultat.size());
    }

    @Test
    void testFindByTrenerId() {
        when(entityManager.createQuery(anyString(), eq(TrenerStrucnost.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("idTrener"), eq(1L))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(List.of(odnos(new TrenerStrucnostId(1L, 2L))));

        List<TrenerStrucnost> rezultat = repository.findByTrenerId(1L);

        assertEquals(1, rezultat.size());
    }
}
