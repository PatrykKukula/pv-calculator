package com.github.PatrykKukula.Photovoltaic.materials.calculator.MaterialBuilder.ElectricalMaterialBuilder;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.InstallationMaterialAssembler.Electrical.ElectricalMaterialAssembler;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.InstallationMaterialAssembler.Electrical.ElectricalMaterialFactory;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.InstallationMaterial;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ElectricalMaterialAssemblerTest {
    @Mock
    private ElectricalMaterialFactory electricalMaterialFactory;
    @InjectMocks
    private ElectricalMaterialAssembler electricalMaterialAssembler;

    @BeforeEach
    public void setUp(){
        electricalMaterialAssembler = new ElectricalMaterialAssembler(electricalMaterialFactory);
    }

    @Test
    @DisplayName("Should create installation electrical materials correctly")
    public void shouldCreateInstallationElectricalMaterialsCorrectly(){
        List<InstallationMaterial> materials = electricalMaterialAssembler.createInstallationElectricalMaterials();

        Assertions.assertEquals(14, materials.size());
        verify(electricalMaterialFactory, times(1)).createPhotovoltaicModule();
        verify(electricalMaterialFactory, times(1)).createInverter();
        verify(electricalMaterialFactory, times(1)).createDcSwitchboard();
        verify(electricalMaterialFactory, times(1)).createAcSwitchboard();
        verify(electricalMaterialFactory, times(1)).createAcCable();
        verify(electricalMaterialFactory, times(1)).createDcCable();
        verify(electricalMaterialFactory, times(1)).createDcFuse();
        verify(electricalMaterialFactory, times(1)).createDcFuseHolder();
        verify(electricalMaterialFactory, times(1)).createDcSurgeArresters();
        verify(electricalMaterialFactory, times(1)).createAcSurgeArrester();
        verify(electricalMaterialFactory, times(1)).createLgyCable();
        verify(electricalMaterialFactory, times(1)).createDifferentialCircuitBreaker();
        verify(electricalMaterialFactory, times(1)).createOvercurrentCircuitBreakerB();
        verify(electricalMaterialFactory, times(1)).createOvercurrentCircuitBreakerC();
        verifyNoMoreInteractions(electricalMaterialFactory);
    }
}
