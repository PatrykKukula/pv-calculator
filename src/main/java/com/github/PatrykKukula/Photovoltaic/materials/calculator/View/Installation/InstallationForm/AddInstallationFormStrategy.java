package com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Installation.InstallationForm;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationInterface;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Installation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.InstallationService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AddInstallationFormStrategy implements InstallationFormStrategy<InstallationDto> {

    @Override
    public String getTitle() {
        return "Fill form to add installation";
    }
    @Override
    public void save(InstallationService installationService, InstallationInterface installationInterface, ProjectDto projectDto, Long installationId) {
        Installation savedInstallation = installationService.createInstallation((InstallationDto) installationInterface, projectDto);
        log.info("Installation saved successfully:{} ", savedInstallation);
    }
    @Override
    public Class<InstallationDto> getDtoClass() {
        return InstallationDto.class;
    }
}
