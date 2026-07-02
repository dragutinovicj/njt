/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Repository;
import rs.acflash.entity.impl.Trener;
import rs.acflash.exception.NjtException;
import rs.acflash.repository.AppRepository;

/**
 *
 * @author Korisnik
 */
@Repository
public class TrenerRepository implements AppRepository<Trener, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Trener> findAll() {
        return entityManager.createQuery("select t from Trener t", Trener.class).getResultList();
    }

    @Override
    public Trener findById(Long id) throws NjtException {
        Trener t = entityManager.find(Trener.class, id);
        if (t == null) {
            throw new NjtException("Trener nije pronađen");
        }
        return t;
    }

    @Override
    @Transactional
    public void save(Trener entity) {
        if (entity.getIdTrener() == null) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Trener t = entityManager.find(Trener.class, id);
        if (t != null) {
            entityManager.remove(t);
        }
    }

    public Object findByUsername(String username) {

        try {
            return entityManager.createQuery(
                    "SELECT t FROM Trener t WHERE t.korisnickoIme = :username", Trener.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;

        }
    }
    
    public boolean existsByUsername(String username) {
    Long count = entityManager.createQuery(
            "SELECT COUNT(t) FROM Trener t WHERE t.korisnickoIme = :username", Long.class)
            .setParameter("username", username)
            .getSingleResult();

    return count != null && count > 0;
}


}
