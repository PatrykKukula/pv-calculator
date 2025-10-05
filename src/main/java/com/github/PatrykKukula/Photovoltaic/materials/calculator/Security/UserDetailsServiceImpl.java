package com.github.PatrykKukula.Photovoltaic.materials.calculator.Security;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.UserEntity;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserEntityRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = findUserByUsername(username);

        return new User(
                username,
                user.getPassword(),
                mapRolesToAuthorities(user)
        );
    }
    private List<? extends GrantedAuthority> mapRolesToAuthorities(UserEntity user){
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .toList();
    }
    private UserEntity findUserByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username %s not found".formatted(username)));
    }
}
