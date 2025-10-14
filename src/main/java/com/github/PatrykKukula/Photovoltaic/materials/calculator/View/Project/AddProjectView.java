package com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Project;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.ProjectService;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Project.ProjectForm.ProjectFormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Route("/projects/add")
@PageTitle("PV calculator - add project")
@RolesAllowed({"USER", "ADMIN"})
@RequiredArgsConstructor
public class AddProjectView extends VerticalLayout implements HasUrlParameter<Long> {
    private final ProjectService projectService;
    private ProjectDto projectDto = new ProjectDto();

    @Override
    public void setParameter(BeforeEvent event, Long userId) {
        getStyle().set("width", "40%").set("height", "100%").set("margin", "auto");

        ProjectFormLayout<ProjectDto> layout = new ProjectFormLayout<>(projectService, projectDto, null);
        layout.configureFormLayout(userId);
        add(layout);
    }
}