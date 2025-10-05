package com.github.PatrykKukula.Photovoltaic.materials.calculator.Mapper;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ModuleOrientation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationUpdateDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Installation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Row;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InstallationMapperTest {
    private InstallationDto installationDto;
    private Installation installation;
    private InstallationUpdateDto installationUpdateDto;

    @BeforeEach
    public void setUp() {
        installationDto = InstallationDto.builder()
                .moduleOrientation(ModuleOrientation.VERTICAL)
                .rows(List.of(Row.builder().rowNumber(1L).moduleQuantity(10L).build()))
                .build();
        installation = Installation.builder()
                .moduleOrientation(ModuleOrientation.VERTICAL)
                .rows(List.of(Row.builder().rowNumber(1L).moduleQuantity(10L).build()))
                .build();
        installationUpdateDto = InstallationUpdateDto.builder()
                .moduleOrientation(ModuleOrientation.HORIZONTAL)
                .rows(List.of(Row.builder().rowNumber(1L).moduleQuantity(12L).build()))
                .build();
    }
    @Test
    @DisplayName("Should map InstallationDto to Installation correctly")
    public void shouldMapInstallationDtoToInstallationCorrectly(){
        Installation mappedInstallation = InstallationMapper.mapInstallationDtoToInstallation(installationDto);

        assertEquals(ModuleOrientation.VERTICAL, mappedInstallation.getModuleOrientation());
        assertEquals(1, mappedInstallation.getRows().size());
        assertEquals(1, mappedInstallation.getRows().getFirst().getRowNumber());
    }
    @Test
    @DisplayName("Should map Installation to InstallationDto correctly")
    public void shouldMapInstallationToInstallationDtoCorrectly(){
        InstallationDto mappedInstallation = InstallationMapper.mapInstallationToInstallationDto(installation);

        assertEquals(ModuleOrientation.VERTICAL, mappedInstallation.getModuleOrientation());
        assertEquals(1, mappedInstallation.getRows().size());
        assertEquals(1, mappedInstallation.getRows().getFirst().getRowNumber());
    }
    @Test
    @DisplayName("Should map InstallationUpdateDto to Installation correctly")
    public void shouldMapInstallationUpdateDtoToInstallationCorrectly(){
        Installation mappedInstallation = InstallationMapper.mapInstallationUpdateDtoToInstallation(installationUpdateDto, installation);

        assertEquals(ModuleOrientation.HORIZONTAL, mappedInstallation.getModuleOrientation());
        assertEquals(1, mappedInstallation.getRows().size());
        assertEquals(12, mappedInstallation.getRows().getFirst().getModuleQuantity());
    }
    @Test
    @DisplayName("Should not change Installation when map InstallationUpdateDto to Installation with null values")
    public void shouldNotChangeInstallationWhenMapInstallationUpdateDtoToInstallationWithNullValues(){
        Installation mappedInstallation = InstallationMapper.mapInstallationUpdateDtoToInstallation(new InstallationUpdateDto(), installation);

        assertEquals(ModuleOrientation.VERTICAL, mappedInstallation.getModuleOrientation());
        assertEquals(1, mappedInstallation.getRows().size());
        assertEquals(10, mappedInstallation.getRows().getFirst().getModuleQuantity());
    }
}
