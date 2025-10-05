package com.github.PatrykKukula.Photovoltaic.materials.calculator.Service;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository.InstallationMaterialRepository;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository.InstallationRepository;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Security.UserDetailsServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class InstallationServiceTest {
    @Mock
    private InstallationRepository installationRepository;
    @Mock
    private InstallationMaterialRepository installationMaterialRepository;
    @Mock
    private MaterialService  materialService;
    @Mock
    private UserDetailsServiceImpl userDetailsService;
    @InjectMocks
    private InstallationService installationService;
}
