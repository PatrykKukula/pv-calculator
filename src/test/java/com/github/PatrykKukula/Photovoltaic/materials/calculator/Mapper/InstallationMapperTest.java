package com.github.PatrykKukula.Photovoltaic.materials.calculator.Mapper;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ModuleOrientation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationUpdateDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.RowDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Installation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Project;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Row;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InstallationMapperTest {
    private InstallationDto installationDto;
    private Installation installation;
    private InstallationUpdateDto installationUpdateDto;
    private List<Row> rows = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        rows.add(Row.builder().rowNumber(1L).moduleQuantity(10L).build());
        installationDto = InstallationDto.builder()
                .moduleOrientation(ModuleOrientation.VERTICAL)
                .address("address")
                .rows(List.of(new RowDto(1L, 10L)))
                .build();
        installation = Installation.builder()
                .moduleOrientation(ModuleOrientation.VERTICAL)
                .address("address")
                .rows(rows)
                .project(Project.builder().projectId(1L).build())
                .build();
        installationUpdateDto = InstallationUpdateDto.builder()
                .moduleOrientation(ModuleOrientation.HORIZONTAL)
                .address("address1")
                .rows(List.of(new RowDto(1L, 12L)))
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
        assertEquals("address1", mappedInstallation.getAddress());
    }
    @Test
    @DisplayName("Should map Installation to Installation update dto correctly")
    public void shouldMapInstallationToInstallationUpdateDtoCorrectly(){
        InstallationUpdateDto mappedInstallation = InstallationMapper.mapInstallationToInstallationUpdateDto(installation);

        assertEquals(10, mappedInstallation.getRows().getFirst().getModuleQuantity());
        assertEquals("address", mappedInstallation.getAddress());
    }
}
