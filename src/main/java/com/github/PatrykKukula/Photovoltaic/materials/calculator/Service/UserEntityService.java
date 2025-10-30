package com.github.PatrykKukula.Photovoltaic.materials.calculator.Service;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.User.LoginDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.User.RegisterDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception.UsernameAlreadyExistException;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.UserEntity;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository.RoleRepository;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository.UserEntityRepository;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Utils.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.CacheConstants.USER_VAADIN;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserEntityService {
    private final UserEntityRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final UserValidator userValidator;
    private final String DEFAULT_ROLE = "USER";

    public UserEntity loadCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new AuthenticationCredentialsNotFoundException("Log in to proceed");
        }
        if (!auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            throw new AuthenticationCredentialsNotFoundException("Log in to proceed");
        }
        Object principal = auth.getPrincipal();
        if (principal instanceof UserDetails user){
            String username = user.getUsername();
            return userRepository.findByUsernameWithRoles(username).orElseThrow(() -> new UsernameNotFoundException("Invalid login or password"));
        }
        else {
            throw new AuthenticationCredentialsNotFoundException("Log in to proceed");
        }
    }
    @Cacheable(
            value = USER_VAADIN,
            key = "T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication() != null ? T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication().getName() : 'ANON'",
            unless = "#result == null"
    )
    public Optional<UserEntity> loadCurrentUserForVaadin(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return Optional.empty();
        if (!auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) return Optional.empty();

        Object principal = auth.getPrincipal();
        if (principal instanceof UserDetails user){
            String username = user.getUsername();
            return userRepository.findByUsernameWithRoles(username);
        }
        return Optional.empty();
    }
    public boolean isUserAnonymous(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken;
    }
    public boolean register(RegisterDto registerDto){
        userValidator.validateRegisterData(registerDto);

        String hashedPassword = passwordEncoder.encode(registerDto.getPassword());
        UserEntity user = UserEntity.builder()
                .username(registerDto.getUsername())
                .password(hashedPassword)
                .roles(List.of(roleRepository.findByName(DEFAULT_ROLE).orElseThrow(() ->
                        new RuntimeException("Error creating account - role %s not found. Please contact administrator".formatted(DEFAULT_ROLE)))))
                .build();
        try {
            userRepository.save(user);
        }
        catch (DataIntegrityViolationException ex){
            throw new UsernameAlreadyExistException(registerDto.getUsername());
        }
        return true;
    }
    public boolean login(LoginDto loginDto){
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return true;
    }
}
