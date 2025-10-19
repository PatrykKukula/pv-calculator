package com.github.PatrykKukula.Photovoltaic.materials.calculator.Service;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.User.LoginDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.User.RegisterDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception.UsernameAlreadyExistException;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Role;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.UserEntity;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository.RoleRepository;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository.UserEntityRepository;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Utils.UserValidator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserEntityServiceTest {
    @Mock
    private UserEntityRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserValidator userValidator;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private UserEntityService userEntityService;
    private UserEntity userEntity;
    private User user;
    private TestingAuthenticationToken testingAuthenticationToken;
    private SecurityContext context;
    private RegisterDto registerDto;
    private LoginDto loginDto;

    @BeforeEach
    public void setUp(){
        userEntity = UserEntity.builder()
                .username("user")
                .password("password")
                .roles(List.of(Role.builder().name("USER").build()))
                .build();
        user = new User("user", "", List.of(new SimpleGrantedAuthority("ROLE_USER")));
        testingAuthenticationToken = new TestingAuthenticationToken(user, "");
        testingAuthenticationToken.setAuthenticated(true);
        context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(testingAuthenticationToken);
        SecurityContextHolder.setContext(context);

        registerDto = RegisterDto.builder()
                .username("username")
                .password("Password123!")
                .build();
        loginDto = LoginDto.builder()
                .username("username")
                .password("Password123!")
                .build();
    }
    @AfterEach
    public void clear(){
        SecurityContextHolder.clearContext();
    }
    @Test
    @DisplayName("Should load current User correctly")
    public void shouldLoadCurrentUserCorrectly(){
        when(userRepository.findByUsernameWithRoles(anyString())).thenReturn(Optional.of(userEntity));

        UserEntity loadedUser = userEntityService.loadCurrentUser();

        assertEquals("user", loadedUser.getUsername());
    }
    @Test
    @DisplayName("Should load current User for Vaadin correctly")
    public void shouldLoadCurrentUserForVaadinCorrectly(){
        when(userRepository.findByUsernameWithRoles(anyString())).thenReturn(Optional.of(userEntity));

        UserEntity loadedUser = userEntityService.loadCurrentUser();

        assertEquals("user", loadedUser.getUsername());
    }
    @Test
    @DisplayName("Should throw AuthenticationCredentialsNotFoundException when loadCurrentUser and Authentication is null")
    public void shouldThrowAuthenticationCredentialsNotFoundExceptionWhenLoadCurrentUserAndAuthenticationIsNull(){
        context.setAuthentication(null);

        AuthenticationCredentialsNotFoundException ex = assertThrows(AuthenticationCredentialsNotFoundException.class, () -> userEntityService.loadCurrentUser());
        assertEquals("Log in to proceed", ex.getMessage());
    }
    @Test
    @DisplayName("Should throw AuthenticationCredentialsNotFoundException when loadCurrentUser and user is anonymous")
    public void shouldThrowAuthenticationCredentialsNotFoundExceptionWhenLoadCurrentUserAndUserIsAnonymous(){
        SecurityContextHolder.getContext().setAuthentication(new AnonymousAuthenticationToken("key", "anonymousUser", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS")));

        AuthenticationCredentialsNotFoundException ex = assertThrows(AuthenticationCredentialsNotFoundException.class, () -> userEntityService.loadCurrentUser());
        assertEquals("Log in to proceed", ex.getMessage());
    }
    @Test
    @DisplayName("Should throw AuthenticationCredentialsNotFoundException when loadCurrentUser and Authentication is not authenticated")
    public void shouldThrowAuthenticationCredentialsNotFoundExceptionWhenLoadCurrentUserAndAuthenticationIsNotAuthenticated(){
        testingAuthenticationToken.setAuthenticated(false);

        AuthenticationCredentialsNotFoundException ex = assertThrows(AuthenticationCredentialsNotFoundException.class, () -> userEntityService.loadCurrentUser());
        assertEquals("Log in to proceed", ex.getMessage());
    }
    @Test
    @DisplayName("Should throw UsernameNotFoundException when loadCurrentUser and user not found")
    public void shouldThrowUsernameNotFoundExceptionWhenLoadCurrentUserAndUserNotFound(){
        when(userRepository.findByUsernameWithRoles(anyString())).thenReturn(Optional.empty());

        UsernameNotFoundException ex = assertThrows(UsernameNotFoundException.class, () -> userEntityService.loadCurrentUser());
        assertEquals("Invalid login or password", ex.getMessage());
    }
    @Test
    @DisplayName("Should return isUserAnonymous correctly")
    public void shouldReturnIsUserAnonymousCorrectly(){
        boolean userAnonymous = userEntityService.isUserAnonymous();

        assertFalse(userAnonymous);
    }
    @Test
    @DisplayName("Should register correctly")
    public void shouldRegisterCorrectly(){
        doNothing().when(userValidator).validateRegisterData(any(RegisterDto.class));
        when(passwordEncoder.encode(anyString())).thenReturn("hashed password");
        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(Role.builder().name("USER").build()));
        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);

        boolean registered = userEntityService.register(registerDto);

        verify(userRepository).save(captor.capture());
        UserEntity registeredUser = captor.getValue();

        assertTrue(registered);
        assertEquals("username", registeredUser.getUsername());
        assertEquals("hashed password", registeredUser.getPassword());
    }
    @Test
    @DisplayName("Should throw IllegalArgumentException when register and username is invalid")
    public void shouldThrowIllegalArgumentExceptionWhenRegisterAndUsernameIsInvalid(){
        doThrow(new IllegalArgumentException("Username must contain only alphanumeric characters and be between 3-64 characters long"))
                .when(userValidator).validateRegisterData(any(RegisterDto.class));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> userEntityService.register(registerDto));
        assertEquals("Username must contain only alphanumeric characters and be between 3-64 characters long", ex.getMessage());
    }
    @Test
    @DisplayName("Should throw UsernameAlreadyExistException when register and username already exists")
    public void shouldThrowUsernameAlreadyExistExceptionWhenRegisterAndUsernameAlreadyExists(){
        doNothing().when(userValidator).validateRegisterData(any(RegisterDto.class));
        when(passwordEncoder.encode(anyString())).thenReturn("hashed password");
        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(Role.builder().name("USER").build()));
        when(userRepository.save(any())).thenThrow(new DataIntegrityViolationException("Username already exists"));

        UsernameAlreadyExistException ex = assertThrows(UsernameAlreadyExistException.class, () -> userEntityService.register(registerDto));
        assertEquals("Username already exists", ex.getUserMessage());
    }
    @Test
    @DisplayName("Should login correctly")
    public void shouldLoginCorrectly(){
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(testingAuthenticationToken);

        boolean login = userEntityService.login(loginDto);

        assertTrue(login);
    }

}
