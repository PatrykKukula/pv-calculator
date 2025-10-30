package com.github.PatrykKukula.Photovoltaic.materials.calculator.MaterialBuilder.ElectricalMaterialBuilder;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ConstructionType;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ModuleOrientation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.PhaseNumber;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.InstallationMaterialAssembler.Electrical.ElectricalMaterialFactory;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Installation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.InstallationMaterial;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Row;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.MaterialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ElectricalMaterialFactoryTest {
    @Mock
    private MaterialService materialService;
    private ElectricalMaterialFactory electricalMaterialFactory;
    private Installation installation;
    private Long modulePower;
    private Row row;

    @BeforeEach
    public void setUp(){
        row = Row.builder()
                .rowId(1L)
                .rowNumber(1L)
                .moduleQuantity(10L)
                .build();
        List<Row> rows = new ArrayList<>();
        rows.add(row);
        installation = Installation.builder()
                .installationId(1L)
                .address("address")
                .installationType(ConstructionType.TRAPEZE)
                .moduleOrientation(ModuleOrientation.VERTICAL)
                .phaseNumber(PhaseNumber.THREE_PHASE)
                .strings(2)
                .createdAt(LocalDateTime.of(2000, 1, 1, 12, 0))
                .rows(rows)
                .lightingProtection(true)
                .acCableLength(10)
                .dcCableLength(100)
                .lgyCableLength(10)
                .build();
        modulePower = 500L;
        electricalMaterialFactory = new ElectricalMaterialFactory(materialService, installation, modulePower);
    }
    @Test
    @DisplayName("Should create photovoltaic module correctly")
    public void shouldCreatePhotovoltaicModuleCorrectly(){
        when(materialService.createElectricalMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        electricalMaterialFactory.createPhotovoltaicModule();

        verify(materialService).createElectricalMaterial(eq("Photovoltaic module"), eq(10L), eq(installation));
    }
    @Test
    @DisplayName("Should create three phased inverter correctly")
    public void shouldCreateThreePhasedInverterCorrectly(){
        when(materialService.createElectricalMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        electricalMaterialFactory.createInverter();

        verify(materialService).createElectricalMaterial(eq("3 phased inverter 5 kW"), eq(1L), eq(installation));
    }
    @Test
    @DisplayName("Should create single phased inverter correctly")
    public void shouldCreateSinglePhasedInverterCorrectly(){
        installation.setRows(List.of(Row.builder().rowNumber(1L).moduleQuantity(5L).build()));
        installation.setPhaseNumber(PhaseNumber.SINGLE_PHASE);
        when(materialService.createElectricalMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        electricalMaterialFactory.createInverter();

        verify(materialService).createElectricalMaterial(eq("1 phased inverter 3,00 kW"), eq(1L), eq(installation));
    }
    @Test
    @DisplayName("Should create DC switchboard correctly")
    public void shouldCreateDcSwitchboardCorrectly(){
        when(materialService.createElectricalMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        electricalMaterialFactory.createDcSwitchboard();

        verify(materialService).createElectricalMaterial(eq("DC switchboard 12P"), eq(1L), eq(installation));
    }
    @Test
    @DisplayName("Should create AC switchboard correctly")
    public void shouldCreateAcSwitchboardCorrectly(){
        when(materialService.createElectricalMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        electricalMaterialFactory.createAcSwitchboard();

        verify(materialService).createElectricalMaterial(eq("AC switchboard 12P"), eq(1L), eq(installation));
    }
    @Test
    @DisplayName("Should create AC cable three phased correctly min value")
    public void shouldCreateAcCableThreePhasedCorrectlyMinValue(){
        when(materialService.createElectricalMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        electricalMaterialFactory.createAcCable();

        verify(materialService).createElectricalMaterial(eq("AC cable 5x2.5mm2"), eq(10L), eq(installation));
    }
    @Test
    @DisplayName("Should create AC cable three phased correctly min value")
    public void shouldCreateAcCableThreePhasedCorrectlyMaxValue(){
        installation.setAcCableLength(10000);
        when(materialService.createElectricalMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        electricalMaterialFactory.createAcCable();

        verify(materialService).createElectricalMaterial(eq("AC cable 5x25.0mm2"), eq(10000L), eq(installation));
    }
    @Test
    @DisplayName("Should create AC cable single phased correctly min value")
    public void shouldCreateAcCableSinglePhasedCorrectlyMinValue(){
        installation.setPhaseNumber(PhaseNumber.SINGLE_PHASE);
        installation.setRows(List.of(Row.builder().rowNumber(1L).moduleQuantity(5L).build()));
        when(materialService.createElectricalMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        electricalMaterialFactory.createAcCable();

        verify(materialService).createElectricalMaterial(eq("AC cable 3x2.5mm2"), eq(10L), eq(installation));
    }
    @Test
    @DisplayName("Should create DC cable correctly under 10 kW installation")
    public void shouldCreateDcCableCorrectlyUnder10KwInstallation(){
        when(materialService.createElectricalMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        electricalMaterialFactory.createDcCable();

        verify(materialService).createElectricalMaterial(eq("DC cable 4mm2"), eq(100L), eq(installation));
    }
    @Test
    @DisplayName("Should create DC cable correctly over 10 kW installation")
    public void shouldCreateDcCableCorrectlyOver10KwInstallation(){
        installation.setRows(List.of(Row.builder().rowNumber(1L).moduleQuantity(21L).build()));
        when(materialService.createElectricalMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        electricalMaterialFactory.createDcCable();

        verify(materialService).createElectricalMaterial(eq("DC cable 6mm2"), eq(100L), eq(installation));
    }
    @Test
    @DisplayName("Should create DC fuse correctly")
    public void shouldCreateDcFuseCorrectly(){
        installation.setRows(List.of(Row.builder().rowNumber(1L).moduleQuantity(21L).build()));
        when(materialService.createElectricalMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        electricalMaterialFactory.createDcFuse();

        verify(materialService).createElectricalMaterial(eq("DC fuse 15A"), eq(4L), eq(installation));
    }
    @Test
    @DisplayName("Should create DC fuse holder correctly")
    public void shouldCreateDcFuseHolderCorrectly(){
        installation.setRows(List.of(Row.builder().rowNumber(1L).moduleQuantity(21L).build()));
        when(materialService.createElectricalMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        electricalMaterialFactory.createDcFuseHolder();

        verify(materialService).createElectricalMaterial(eq("DC fuse holder 2p"), eq(2L), eq(installation));
    }
    @Test
    @DisplayName("Should create DC surge arrester type 1+2 correctly")
    public void shouldCreateDcSurgeArresterType12Correctly(){
        when(materialService.createElectricalMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        electricalMaterialFactory.createDcSurgeArresters();

        verify(materialService).createElectricalMaterial(eq("Surge arrester DC 3P T1+2"), eq(2L), eq(installation));
    }
    @Test
    @DisplayName("Should create DC surge arrester type 2 correctly")
    public void shouldCreateDcSurgeArresterType2Correctly(){
        installation.setLightingProtection(false);
        when(materialService.createElectricalMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        electricalMaterialFactory.createDcSurgeArresters();

        verify(materialService).createElectricalMaterial(eq("Surge arrester DC 3P T2"), eq(2L), eq(installation));
    }
    @Test
    @DisplayName("Should create AC surge arrester type 1+2 three phased correctly")
    public void shouldCreateAcSurgeArresterType12ThreePhasedCorrectly(){
        when(materialService.createElectricalMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        electricalMaterialFactory.createAcSurgeArrester();

        verify(materialService).createElectricalMaterial(eq("Surge arrester AC 4P T1+2"), eq(1L), eq(installation));
    }
    @Test
    @DisplayName("Should create AC surge arrester type 1+2 single phased correctly")
    public void shouldCreateAcSurgeArresterType12SinglePhasedCorrectly(){
        installation.setPhaseNumber(PhaseNumber.SINGLE_PHASE);
        when(materialService.createElectricalMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        electricalMaterialFactory.createAcSurgeArrester();

        verify(materialService).createElectricalMaterial(eq("Surge arrester AC 2P T1+2"), eq(1L), eq(installation));
    }
    @Test
    @DisplayName("Should create LgY cable 16mm2 correctly")
    public void shouldCreateLgyCable16Mm2Correctly(){
        when(materialService.createElectricalMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        electricalMaterialFactory.createLgyCable();

        verify(materialService).createElectricalMaterial(eq("LgY cable 16 mm2"), eq(10L), eq(installation));
    }
    @Test
    @DisplayName("Should create LgY cable 6mm2 correctly")
    public void shouldCreateLgyCable6Mm2Correctly(){
        installation.setLightingProtection(false);
        when(materialService.createElectricalMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        electricalMaterialFactory.createLgyCable();

        verify(materialService).createElectricalMaterial(eq("LgY cable 6 mm2"), eq(10L), eq(installation));
    }
    @Test
    @DisplayName("Should create differential circuit breaker three phased correctly")
    public void shouldCreateDifferentialCircuitBreakerThreePhasedCorrectly(){
        when(materialService.createElectricalMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        electricalMaterialFactory.createDifferentialCircuitBreaker();

        verify(materialService).createElectricalMaterial(eq("Differential circuit breaker 4P 20/0,1A"), eq(1L), eq(installation));
    }
    @Test
    @DisplayName("Should create differential circuit breaker single phased correctly")
    public void shouldCreateDifferentialCircuitBreakerSinglePhasedCorrectly(){
        installation.setPhaseNumber(PhaseNumber.SINGLE_PHASE);
        installation.setRows(List.of(Row.builder().rowNumber(1L).moduleQuantity(5L).build()));
        when(materialService.createElectricalMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        electricalMaterialFactory.createDifferentialCircuitBreaker();

        verify(materialService).createElectricalMaterial(eq("Differential circuit breaker 2P 20/0,1A"), eq(1L), eq(installation));
    }
    @Test
    @DisplayName("Should create overcurrent circuit breaker B three phased correctly")
    public void shouldCreateOvercurrentCircuitBreakerBThreePhasedCorrectly(){
        when(materialService.createElectricalMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        electricalMaterialFactory.createOvercurrentCircuitBreakerB();

        verify(materialService).createElectricalMaterial(eq("Overcurrent circuit breaker 3P B10"), eq(1L), eq(installation));
    }
    @Test
    @DisplayName("Should create overcurrent circuit breaker B single phased correctly")
    public void shouldCreateOvercurrentCircuitBreakerBSinglePhasedCorrectly(){
        installation.setPhaseNumber(PhaseNumber.SINGLE_PHASE);
        installation.setRows(List.of(Row.builder().rowNumber(1L).moduleQuantity(5L).build()));
        when(materialService.createElectricalMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        electricalMaterialFactory.createOvercurrentCircuitBreakerB();

        verify(materialService).createElectricalMaterial(eq("Overcurrent circuit breaker 1P B16"), eq(1L), eq(installation));
    }
    @Test
    @DisplayName("Should create overcurrent circuit breaker C three phased correctly")
    public void shouldCreateOvercurrentCircuitBreakerCThreePhasedCorrectly(){
        when(materialService.createElectricalMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        electricalMaterialFactory.createOvercurrentCircuitBreakerC();

        verify(materialService).createElectricalMaterial(eq("Overcurrent circuit breaker 3P C10"), eq(1L), eq(installation));
    }
    @Test
    @DisplayName("Should create overcurrent circuit breaker C single phased correctly")
    public void shouldCreateOvercurrentCircuitBreakerCSinglePhasedCorrectly(){
        installation.setPhaseNumber(PhaseNumber.SINGLE_PHASE);
        installation.setRows(List.of(Row.builder().rowNumber(1L).moduleQuantity(5L).build()));
        when(materialService.createElectricalMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        electricalMaterialFactory.createOvercurrentCircuitBreakerC();

        verify(materialService).createElectricalMaterial(eq("Overcurrent circuit breaker 1P C16"), eq(1L), eq(installation));
    }








}
