package com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Installation;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationUpdateDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.InstallationService;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.ProjectService;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Installation.InstallationForm.InstallationFormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;

@Route("/installation/update")
@PageTitle("Update installation")
@RolesAllowed({"USER", "ADMIN"})
@RequiredArgsConstructor
public class InstallationUpdateView extends VerticalLayout implements HasUrlParameter<Long> {
    private final InstallationService installationService;
    private final ProjectService projectService;

    @Override
    public void setParameter(BeforeEvent event, Long installationId) {
        InstallationUpdateDto installation = installationService.findInstallationUpdateById(installationId);
        ProjectDto project = projectService.findProjectById(installation.getProjectId());
        InstallationFormLayout<InstallationUpdateDto> installationUpdateDtoInstallationFormLayout = new InstallationFormLayout<>(installation, installationService, project, project.getProjectId());

        add(installationUpdateDtoInstallationFormLayout);
    }
}
