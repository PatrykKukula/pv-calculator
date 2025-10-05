package com.github.PatrykKukula.Photovoltaic.materials.calculator.Mapper;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationUpdateDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Installation;


public class InstallationMapper {
    public static Installation mapInstallationDtoToInstallation(InstallationDto installationDto){
        return Installation.builder()
                .moduleOrientation(installationDto.getModuleOrientation())
                .rows(installationDto.getRows())
                .build();
    }
    public static InstallationDto mapInstallationToInstallationDto(Installation installation){
        return InstallationDto.builder()
                .installationType(installation.getInstallationType())
                .moduleOrientation(installation.getModuleOrientation())
                .rows(installation.getRows())
                .build();
    }
    public static Installation mapInstallationUpdateDtoToInstallation(InstallationUpdateDto installationUpdateDto, Installation installation){
        if(installationUpdateDto.getInstallationType() != null) installation.setInstallationType(installationUpdateDto.getInstallationType());
        if (installationUpdateDto.getModuleOrientation() != null) installation.setModuleOrientation(installationUpdateDto.getModuleOrientation());
        if (installationUpdateDto.getRows() != null) installation.setRows(installationUpdateDto.getRows());
        return installation;
    }

}
