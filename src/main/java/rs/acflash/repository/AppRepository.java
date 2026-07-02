/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.acflash.repository;

import java.util.List;

/**
 *
 * @author Korisnik
 */
public interface AppRepository<E, T> {
    List<E> findAll();
    E findById(T id) throws Exception;
    void save(E entity);
    void deleteById(T id);
    
}
