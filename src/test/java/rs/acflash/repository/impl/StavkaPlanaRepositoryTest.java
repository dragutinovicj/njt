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
import rs.acflash.entity.impl.PlanTreninga;
import rs.acflash.entity.impl.StavkaPlana;
import rs.acflash.entity.impl.StavkaPlanaId;
import rs.acflash.entity.impl.Vezba;
import rs.acflash.exception.NjtException;

@ExtendWith(MockitoExtension.class)
class StavkaPlanaRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<StavkaPlana> typedQuery;

    @InjectMocks
    private StavkaPlanaRepository repository;

    private StavkaPlana stavka(StavkaPlanaId id) {
        return new StavkaPlana(id, new PlanTreninga(10L), new Vezba(4L), 3, 12, 5);
    }

    @Test
    void testFindAll() {
        when(entityManager.createQuery(anyString(), eq(StavkaPlana.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(List.of(stavka(new StavkaPlanaId(1L, 10L))));

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void testFindByIdPostoji() throws NjtException {
        StavkaPlanaId id = new StavkaPlanaId(1L, 10L);
        when(entityManager.find(StavkaPlana.class, id)).thenReturn(stavka(id));

        assertEquals(3, repository.findById(id).getBrojSerija());
    }

    @Test
    void testFindByIdNePostojiBacaIzuzetak() {
        StavkaPlanaId id = new StavkaPlanaId(2L, 20L);
        when(entityManager.find(StavkaPlana.class, id)).thenReturn(null);

        assertThrows(NjtException.class, () -> repository.findById(id));
    }

    @Test
    void testSaveNoviPozivaPersist() {
        StavkaPlana s = new StavkaPlana(null, new PlanTreninga(10L), new Vezba(4L), 3, 12, 5);

        repository.save(s);

        verify(entityManager, times(1)).persist(s);
    }

    @Test
    void testSavePostojeciPozivaMerge() {
        StavkaPlana s = stavka(new StavkaPlanaId(1L, 10L));

        repository.save(s);

        verify(entityManager, times(1)).merge(s);
    }

    @Test
    void testDeleteByIdPostojeci() {
        StavkaPlanaId id = new StavkaPlanaId(1L, 10L);
        StavkaPlana s = stavka(id);
        when(entityManager.find(StavkaPlana.class, id)).thenReturn(s);

        repository.deleteById(id);

        verify(entityManager, times(1)).remove(s);
    }
}
