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
import rs.acflash.entity.impl.Kategorija;
import rs.acflash.exception.NjtException;
import rs.acflash.repository.AppRepository;

/**
 *
 * @author Korisnik
 */
@Repository
public class KategorijaRepository implements AppRepository<Kategorija, Long>{
    @PersistenceContext
    private EntityManager entityManager;
            
    @Override
    public List<Kategorija> findAll() {
        return entityManager.createQuery("select k from Kategorija k", Kategorija.class).getResultList();
    }

    @Override
    public Kategorija findById(Long id) throws NjtException {
       Kategorija k = entityManager.find(Kategorija.class, id);
       if(k==null)
           throw new NjtException("Kategorija nije pronađena");
       return k;
    }

    @Override
    @Transactional
    public void save(Kategorija entity) {
       if(entity.getIdKategorija() == null){
           entityManager.persist(entity);
       }else{
           entityManager.merge(entity);
       }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Kategorija k = entityManager.find(Kategorija.class, id);
       if(k!=null)
            entityManager.remove(k);
    }
    
}
