package com.github.PatrykKukula.Photovoltaic.materials.calculator.Service;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.UserEntity;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEntityService {
    private final UserEntityRepository userRepository;

    public UserEntity loadCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) throw new AuthenticationCredentialsNotFoundException("Log in to proceed");
        if (!auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            throw new AuthenticationCredentialsNotFoundException("Log in to proceed");
        }
        Object principal = auth.getPrincipal();
        if (principal instanceof UserDetails user){
            String username = user.getUsername();
            return userRepository.findByUsername(username).orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Log in to proceed"));
        }
        else {
            throw new AuthenticationCredentialsNotFoundException("Log in to proceed");
        }
    }
}
