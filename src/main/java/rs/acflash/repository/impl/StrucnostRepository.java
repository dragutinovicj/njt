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
import rs.acflash.entity.impl.Strucnost;
import rs.acflash.exception.NjtException;
import rs.acflash.repository.AppRepository;

/**
 *
 * @author Korisnik
 */
@Repository
public class StrucnostRepository implements AppRepository<Strucnost, Long>{

     @PersistenceContext
    private EntityManager entityManager;
     
    @Override
    public List<Strucnost> findAll() {
        return entityManager.createQuery("select s from Strucnost s", Strucnost.class).getResultList();
    }

    @Override
    public Strucnost findById(Long id) throws NjtException {
        Strucnost s = entityManager.find(Strucnost.class, id);
        if (s == null) {
            throw new NjtException("Strucnost nije pronađena");
        }
        return s;
    }

    @Override
    @Transactional
    public void save(Strucnost entity) {
        if (entity.getIdStrucnost()== null) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Strucnost s = entityManager.find(Strucnost.class, id);
        if (s != null) {
            entityManager.remove(s);
        }
    }
    
}
