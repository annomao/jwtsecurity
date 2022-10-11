package com.security.jwtsecurity.services;

import com.security.jwtsecurity.models.AppUser;
import com.security.jwtsecurity.models.AppUserDetails;
import com.security.jwtsecurity.repositories.AppUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {

    AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User does not exist");
        }
        return new AppUserDetails(user);
    }
}
