package com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Project;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectUpdateDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.UserEntity;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.ProjectService;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.UserEntityService;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Components.NotificationComponents;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Project.ProjectForm.ProjectFormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Route("/projects/edit")
@PageTitle("PV calculator - edit")
@RolesAllowed({"USER", "ADMIN"})
@RequiredArgsConstructor
public class ProjectUpdateView extends VerticalLayout implements HasUrlParameter<Long> {
    private final ProjectService projectService;
    private final UserEntityService userEntityService;

    @Override
    public void setParameter(BeforeEvent event, Long projectId) {
        getStyle().set("width", "40%").set("height", "100%").set("margin", "auto");

        Optional<UserEntity> userOpt = userEntityService.loadCurrentUserForVaadin();
        if (userOpt.isEmpty()) {
            NotificationComponents.showErrorMessage("Log in to access this resource");
            return;
        }
        Long userId = userOpt.get().getUserId();
        ProjectUpdateDto projectDto = projectService.findProjectToUpdateById(projectId);

        ProjectFormLayout<ProjectUpdateDto> layout = new ProjectFormLayout<>(projectService, projectDto, projectId);

        layout.configureFormLayout(userId);
        add(layout);
    }
}
