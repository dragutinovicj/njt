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
import rs.acflash.entity.impl.Kategorija;
import rs.acflash.exception.NjtException;

@ExtendWith(MockitoExtension.class)
class AtleticarRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Atleticar> typedQuery;

    @InjectMocks
    private AtleticarRepository repository;

    private Atleticar atleticar(Long id) {
        return new Atleticar(id, "Marko", "Markovic", new Date(0), "M", 180.0, 75.0, 23.1, "url", new Kategorija(1L));
    }

    @Test
    void testFindAll() {
        when(entityManager.createQuery(anyString(), eq(Atleticar.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(List.of(atleticar(1L)));

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void testFindByIdPostoji() throws NjtException {
        when(entityManager.find(Atleticar.class, 1L)).thenReturn(atleticar(1L));

        assertEquals("Marko", repository.findById(1L).getIme());
    }

    @Test
    void testFindByIdNePostojiBacaIzuzetak() {
        when(entityManager.find(Atleticar.class, 9L)).thenReturn(null);

        assertThrows(NjtException.class, () -> repository.findById(9L));
    }

    @Test
    void testSaveNoviPozivaPersist() {
        Atleticar a = atleticar(null);

        repository.save(a);

        verify(entityManager, times(1)).persist(a);
    }

    @Test
    void testSavePostojeciPozivaMerge() {
        Atleticar a = atleticar(1L);

        repository.save(a);

        verify(entityManager, times(1)).merge(a);
    }

    @Test
    void testDeleteByIdPostojeci() {
        Atleticar a = atleticar(1L);
        when(entityManager.find(Atleticar.class, 1L)).thenReturn(a);

        repository.deleteById(1L);

        verify(entityManager, times(1)).remove(a);
    }
}
