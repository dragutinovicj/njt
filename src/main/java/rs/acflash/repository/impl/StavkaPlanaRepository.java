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
import rs.acflash.entity.impl.StavkaPlana;
import rs.acflash.entity.impl.StavkaPlanaId;
import rs.acflash.repository.AppRepository;

/**
 *
 * @author Korisnik
 */
@Repository
public class StavkaPlanaRepository implements AppRepository<StavkaPlana, StavkaPlanaId> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<StavkaPlana> findAll() {
        return entityManager.createQuery("select sp from StavkaPlana sp", StavkaPlana.class).getResultList();
    }

    @Override
    public StavkaPlana findById(StavkaPlanaId id) throws Exception {
        StavkaPlana sp = entityManager.find(StavkaPlana.class, id);
        if (sp == null) {
            throw new Exception("Stavka nije pronađena");
        }
        return sp;
    }

    @Override
    @Transactional
    public void save(StavkaPlana entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
    }

    @Override
    @Transactional
    public void deleteById(StavkaPlanaId id) {
        StavkaPlana sp = entityManager.find(StavkaPlana.class, id);
        if (sp != null) {
            entityManager.remove(sp);
        }
    }

}
