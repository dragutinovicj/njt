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
import rs.acflash.entity.impl.Vezba;
import rs.acflash.exception.NjtException;
import rs.acflash.repository.AppRepository;

/**
 *
 * @author Korisnik
 */
@Repository
public class VezbaRepository implements AppRepository<Vezba, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Vezba> findAll() {
        return entityManager.createQuery("select v from Vezba v", Vezba.class).getResultList();
    }

    @Override
    public Vezba findById(Long id) throws NjtException {
        Vezba v = entityManager.find(Vezba.class, id);
        if (v == null) {
            throw new NjtException("Vezba nije pronađena");
        }
        return v;
    }

    @Override
    @Transactional
    public void save(Vezba entity) {
        if (entity.getIdVezba() == null) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Vezba v = entityManager.find(Vezba.class, id);
        if (v != null) {
            entityManager.remove(v);
        }
    }

}
