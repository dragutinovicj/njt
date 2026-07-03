package rs.acflash.repository.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import rs.acflash.entity.impl.Atleticar;
import rs.acflash.entity.impl.PlanTreninga;
import rs.acflash.entity.impl.Trener;
import rs.acflash.exception.NjtException;

@ExtendWith(MockitoExtension.class)
class PlanTreningaRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<PlanTreninga> typedQuery;

    @InjectMocks
    private PlanTreningaRepository repository;

    private PlanTreninga plan(Long id) {
        return new PlanTreninga(id, new Date(0), 3, new Atleticar(1L), new Trener(1L));
    }

    @Test
    void testFindAll() {
        when(entityManager.createQuery(anyString(), eq(PlanTreninga.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(List.of(plan(1L)));

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void testFindByIdPostoji() throws NjtException {
        when(entityManager.find(PlanTreninga.class, 1L)).thenReturn(plan(1L));

        assertEquals(3, repository.findById(1L).getIntenzitet());
    }

    @Test
    void testFindByIdNePostojiBacaIzuzetak() {
        when(entityManager.find(PlanTreninga.class, 9L)).thenReturn(null);

        assertThrows(NjtException.class, () -> repository.findById(9L));
    }

    @Test
    void testSaveNoviPozivaPersist() {
        PlanTreninga p = plan(null);

        repository.save(p);

        verify(entityManager, times(1)).persist(p);
    }

    @Test
    void testSavePostojeciPozivaMerge() {
        PlanTreninga p = plan(1L);

        repository.save(p);

        verify(entityManager, times(1)).merge(p);
    }

    @Test
    void testDeleteByIdPostojeci() {
        PlanTreninga p = plan(1L);
        when(entityManager.find(PlanTreninga.class, 1L)).thenReturn(p);

        repository.deleteById(1L);

        verify(entityManager, times(1)).remove(p);
    }

    @Test
    void testDeleteByIdNepostojeciNeRadiNista() {
        when(entityManager.find(PlanTreninga.class, 99L)).thenReturn(null);

        repository.deleteById(99L);

        verify(entityManager, org.mockito.Mockito.never()).remove(org.mockito.ArgumentMatchers.any());
    }
}
