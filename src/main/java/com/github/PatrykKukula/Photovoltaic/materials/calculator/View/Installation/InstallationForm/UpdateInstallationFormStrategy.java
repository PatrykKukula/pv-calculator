package com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Installation.InstallationForm;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationInterface;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationUpdateDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Installation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.InstallationService;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Installation.InstallationView;
import com.vaadin.flow.component.UI;

import static org.reflections.Reflections.log;

public class UpdateInstallationFormStrategy implements InstallationFormStrategy<InstallationUpdateDto>{

    @Override
    public String getTitle() {
        return "Fill form to update installation data";
    }
    @Override
    public void save(InstallationService installationService, InstallationInterface installationInterface, ProjectDto projectDto, Long installationId) {
        Installation savedInstallation = installationService.updateInstallation(installationId, (InstallationUpdateDto) installationInterface, projectDto);
        log.info("Installation updated successfully:{} ", savedInstallation);
        UI.getCurrent().navigate(InstallationView.class, savedInstallation.getInstallationId());
    }
    @Override
    public Class<InstallationUpdateDto> getDtoClass() {
        return InstallationUpdateDto.class;
    }
    @Override
    public void cancel(Long id) {
        UI.getCurrent().navigate(InstallationView.class, id);
    }
}
