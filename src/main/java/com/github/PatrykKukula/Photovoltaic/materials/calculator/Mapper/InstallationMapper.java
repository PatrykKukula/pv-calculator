package com.github.PatrykKukula.Photovoltaic.materials.calculator.Mapper;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationUpdateDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.RowDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Installation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Row;

import java.util.ArrayList;
import java.util.List;

public class InstallationMapper {
    public static Installation mapInstallationDtoToInstallation(InstallationDto installationDto){
        Installation installation = Installation.builder()
                .installationType(installationDto.getConstructionType())
                .moduleOrientation(installationDto.getModuleOrientation())
                .acCableLength(installationDto.getAcCableLength())
                .dcCableLength(installationDto.getDcCableLength())
                .address(installationDto.getAddress())
                .lgyCableLength(installationDto.getLgyCableLength())
                .lightingProtection(installationDto.isLightingProtection())
                .phaseNumber(installationDto.getPhaseNumber())
                .strings(installationDto.getStrings())
                .materials(new ArrayList<>())
                .rows(new ArrayList<>())
                .build();
        List<Row> rows = mapRowDtoToRow(installationDto, installation);
        installation.setRows(rows);
        return installation;
    }
    public static InstallationDto mapInstallationToInstallationDto(Installation installation){
        List<RowDto> rows = installation.getRows().stream().map(row -> new RowDto(row.getRowNumber(), row.getModuleQuantity())).toList();
        return InstallationDto.builder()
                .projectId(installation.getProject().getProjectId())
                .installationId(installation.getInstallationId())
                .constructionType(installation.getInstallationType())
                .moduleOrientation(installation.getModuleOrientation())
                .rows(rows)
                .acCableLength(installation.getAcCableLength())
                .dcCableLength(installation.getDcCableLength())
                .address(installation.getAddress())
                .lgyCableLength(installation.getLgyCableLength())
                .lightingProtection(installation.isLightingProtection())
                .phaseNumber(installation.getPhaseNumber())
                .strings(installation.getStrings())
                .build();
    }
    public static Installation mapInstallationUpdateDtoToInstallation(InstallationUpdateDto installationUpdateDto, Installation installation){
        installation.setInstallationType(installationUpdateDto.getConstructionType());
        installation.setModuleOrientation(installationUpdateDto.getModuleOrientation());
        installation.setAcCableLength(installationUpdateDto.getAcCableLength());
        installation.setDcCableLength(installation.getDcCableLength());
        installation.setAddress(installationUpdateDto.getAddress());
        installation.setLgyCableLength(installationUpdateDto.getLgyCableLength());
        installation.setLightingProtection(installationUpdateDto.isLightingProtection());
        installation.setPhaseNumber(installationUpdateDto.getPhaseNumber());
        installation.setStrings(installationUpdateDto.getStrings());
        List<Row> rows = mapRowDtoToRowUpdate(installationUpdateDto, installation);
        installation.getRows().clear();
        installation.getRows().addAll(rows);
        return installation;
    }
    public static InstallationUpdateDto mapInstallationToInstallationUpdateDto(Installation installation){
        InstallationUpdateDto installationUpdateDto = InstallationUpdateDto.builder()
                .projectId(installation.getProject().getProjectId())
                .address(installation.getAddress())
                .installationId(installation.getInstallationId())
                .moduleOrientation(installation.getModuleOrientation())
                .constructionType(installation.getInstallationType())
                .acCableLength(installation.getAcCableLength())
                .dcCableLength(installation.getDcCableLength())
                .lgyCableLength(installation.getLgyCableLength())
                .phaseNumber(installation.getPhaseNumber())
                .strings(installation.getStrings())
                .build();

        List<RowDto> rows = installation.getRows().stream().map(row -> new RowDto(row.getRowNumber(), row.getModuleQuantity())).toList();
        installationUpdateDto.setRows(rows);
        return installationUpdateDto;
    }
    private static List<Row> mapRowDtoToRow(InstallationDto installationDto, Installation installation){
        return installationDto.getRows().stream().map(row -> Row.builder().rowNumber(row.getRowNumber())
                .moduleQuantity(row.getModuleQuantity()).installation(installation).build()).toList();
    }
    private static List<Row> mapRowDtoToRowUpdate(InstallationUpdateDto installationDto, Installation installation){
        return  installationDto.getRows().stream().map(row -> Row.builder().rowNumber(row.getRowNumber())
                .moduleQuantity(row.getModuleQuantity()).installation(installation).build()).toList();
    }

}
