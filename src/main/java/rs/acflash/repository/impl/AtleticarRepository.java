/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Repository;
import rs.acflash.entity.impl.Atleticar;
import rs.acflash.exception.NjtException;
import rs.acflash.repository.AppRepository;

/**
 *
 * @author Korisnik
 */
@Repository
public class AtleticarRepository implements AppRepository<Atleticar, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Atleticar> findAll() {
        return entityManager.createQuery("select a from Atleticar a", Atleticar.class).getResultList();
    }

    @Override
    public Atleticar findById(Long id) throws NjtException {
        Atleticar a = entityManager.find(Atleticar.class, id);
        if (a == null) {
            throw new NjtException("Atleticar nije pronađen");
        }
        return a;
    }

    @Override
    @Transactional
    public void save(Atleticar entity) {
        if (entity.getIdAtleticar() == null) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Atleticar a = entityManager.find(Atleticar.class, id);
        if (a != null) {
            entityManager.remove(a);
        }
    }

}
