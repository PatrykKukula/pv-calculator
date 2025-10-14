package com.github.PatrykKukula.Photovoltaic.materials.calculator.Mapper;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationUpdateDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.RowDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Installation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Row;
import java.util.List;

public class InstallationMapper {
    public static Installation mapInstallationDtoToInstallation(InstallationDto installationDto){
        Installation installation = Installation.builder()
                .installationType(installationDto.getInstallationType())
                .moduleOrientation(installationDto.getModuleOrientation())
                .acCableLength(installationDto.getAcCableLength())
                .dcCableLength(installationDto.getDcCableLength())
                .address(installationDto.getAddress())
                .build();
        List<Row> rows = installationDto.getRows().stream().map(row -> Row.builder().rowNumber(row.getRowNumber())
                .moduleQuantity(row.getModuleQuantity()).installation(installation).build()).toList();
        installation.setRows(rows);
        return installation;
    }
    public static InstallationDto mapInstallationToInstallationDto(Installation installation){
        List<RowDto> rows = installation.getRows().stream().map(row -> new RowDto(row.getRowNumber(), row.getModuleQuantity())).toList();
        return InstallationDto.builder()
                .installationType(installation.getInstallationType())
                .moduleOrientation(installation.getModuleOrientation())
                .rows(rows)
                .acCableLength(installation.getAcCableLength())
                .dcCableLength(installation.getDcCableLength())
                .address(installation.getAddress())
                .build();
    }
    public static Installation mapInstallationUpdateDtoToInstallation(InstallationUpdateDto installationUpdateDto, Installation installation){
        if(installationUpdateDto.getInstallationType() != null) installation.setInstallationType(installationUpdateDto.getInstallationType());
        if (installationUpdateDto.getModuleOrientation() != null) installation.setModuleOrientation(installationUpdateDto.getModuleOrientation());
        if (installationUpdateDto.getRows() != null) installation.setRows(installationUpdateDto.getRows());
        if (installationUpdateDto.getAcCableLength() != null) installation.setAcCableLength(installationUpdateDto.getAcCableLength());
        if (installationUpdateDto.getDcCableLength() != null) installation.setDcCableLength(installation.getDcCableLength());
        return installation;
    }

}
