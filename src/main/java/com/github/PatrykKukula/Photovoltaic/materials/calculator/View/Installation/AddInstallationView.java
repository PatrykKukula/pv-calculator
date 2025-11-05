package com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Installation;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.InstallationService;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.ProjectService;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Installation.InstallationForm.InstallationFormLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;

@Route("/installation/add")
@PageTitle("PV calculator - add installation")
@RolesAllowed({"USER", "ADMIN"})
@RequiredArgsConstructor
@CssImport("./styles/common-styles.css")
public class AddInstallationView extends VerticalLayout implements HasUrlParameter<Long> {
    private final InstallationService installationService;
    private final ProjectService projectService;
    private ProjectDto project;
    private final InstallationDto installationDto = new InstallationDto();

    @Override
    public void setParameter(BeforeEvent event, Long projectId) {
        setSizeFull();
        project = projectService.findProjectById(projectId);
        InstallationFormLayout<InstallationDto> installationFormLayout = new InstallationFormLayout<>(installationDto, installationService, project, projectId);
        add(installationFormLayout);
    }
}
