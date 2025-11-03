package com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Installation.InstallationForm;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationInterface;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Installation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.InstallationService;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Installation.InstallationView;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Project.ProjectsView;
import com.vaadin.flow.component.UI;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class AddInstallationFormStrategy implements InstallationFormStrategy<InstallationDto> {

    @Override
    public String getTitle() {
        return "Fill form to add installation";
    }
    @Override
    public void save(InstallationService installationService, InstallationInterface installationInterface, ProjectDto projectDto, Long installationId) throws IOException {
        Installation savedInstallation = installationService.createInstallation((InstallationDto) installationInterface, projectDto);
        log.info("Installation saved successfully:{} ", savedInstallation);
        UI.getCurrent().navigate(InstallationView.class, savedInstallation.getInstallationId());
    }
    @Override
    public Class<InstallationDto> getDtoClass() {
        return InstallationDto.class;
    }
    @Override
    public void cancel(Long id) {
        UI.getCurrent().navigate(ProjectsView.class, id);
    }
}
