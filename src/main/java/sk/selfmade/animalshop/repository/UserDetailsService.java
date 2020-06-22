package sk.selfmade.animalshop.repository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService {
    UserDetails loadUserByUGsername(String username) throws UsernameNotFoundException;
}