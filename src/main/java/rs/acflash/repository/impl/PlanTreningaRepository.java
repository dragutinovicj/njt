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
import rs.acflash.entity.impl.PlanTreninga;
import rs.acflash.repository.AppRepository;

/**
 *
 * @author Korisnik
 */
@Repository
public class PlanTreningaRepository implements AppRepository<PlanTreninga, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PlanTreninga> findAll() {
        return entityManager.createQuery("select p from PlanTreninga p", PlanTreninga.class).getResultList();
    }

    @Override
    public PlanTreninga findById(Long id) throws Exception {
        PlanTreninga p = entityManager.find(PlanTreninga.class, id);
        if (p == null) {
            throw new Exception("Atleticar nije pronađen");
        }
        return p;
    }

    @Override
    @Transactional
    public void save(PlanTreninga entity) {
        if (entity.getIdPlan() == null) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        PlanTreninga p = entityManager.find(PlanTreninga.class, id);
        if (p != null) {
            entityManager.remove(p);
        }
    }

}
