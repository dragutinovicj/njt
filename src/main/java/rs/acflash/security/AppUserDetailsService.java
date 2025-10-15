/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.security;

import java.util.Collections;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.acflash.entity.impl.Trener;
import rs.acflash.repository.impl.TrenerRepository;

/**
 *
 * @author Korisnik
 */
@Service
public class AppUserDetailsService implements UserDetailsService {

    private final TrenerRepository trenerRepository;

    public AppUserDetailsService(TrenerRepository trenerRepository) {
        this.trenerRepository = trenerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Trener t = (Trener) trenerRepository.findByUsername(username);
        if (t == null) {
            throw new UsernameNotFoundException("Trener nije pronađen");
        }

        return new org.springframework.security.core.userdetails.User(
                t.getKorisnickoIme(),
                t.getLozinka(),
                Collections.emptyList() // nema uloga
        );
    }

}
