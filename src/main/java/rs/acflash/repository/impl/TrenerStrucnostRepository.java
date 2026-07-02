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
import rs.acflash.dto.impl.TrenerStrucnostDto;
import rs.acflash.entity.impl.TrenerStrucnost;
import rs.acflash.entity.impl.TrenerStrucnostId;
import rs.acflash.exception.NjtException;
import rs.acflash.repository.AppRepository;

/**
 *
 * @author Korisnik
 */
@Repository
public class TrenerStrucnostRepository implements AppRepository<TrenerStrucnost, TrenerStrucnostId>{

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public List<TrenerStrucnost> findAll() {
       return entityManager.createQuery("select ts from TrenerStrucnost ts", TrenerStrucnost.class).getResultList();
    }

    @Override
    public TrenerStrucnost findById(TrenerStrucnostId id) throws NjtException {
         TrenerStrucnost ts = entityManager.find(TrenerStrucnost.class, id);
        if (ts == null) {
            throw new NjtException("Nije pronađena");
        }
        return ts;
    }

    @Override
    @Transactional
    public void save(TrenerStrucnost entity) {
         if (entity.getId()== null) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
    }

    @Override
    @Transactional
    public void deleteById(TrenerStrucnostId id) {
        TrenerStrucnost ts = entityManager.find(TrenerStrucnost.class, id);
        if (ts != null) {
            entityManager.remove(ts);
        }
    }

    public List<TrenerStrucnost> findByStrucnostId(Long idStrucnost) {
        return entityManager.createQuery(
                "SELECT ts FROM TrenerStrucnost ts WHERE ts.strucnost.idStrucnost = :idStrucnost",
                TrenerStrucnost.class
        )
        .setParameter("idStrucnost", idStrucnost)
        .getResultList();
    }

    public List<TrenerStrucnost> findByTrenerId(Long idTrener) {
        return entityManager.createQuery(
                "SELECT ts FROM TrenerStrucnost ts WHERE ts.trener.idTrener = :idTrener",
                TrenerStrucnost.class
        )
        .setParameter("idTrener", idTrener)
        .getResultList();
    }
    
}
