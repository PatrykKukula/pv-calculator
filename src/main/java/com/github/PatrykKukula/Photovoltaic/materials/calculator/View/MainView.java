package com.github.PatrykKukula.Photovoltaic.materials.calculator.View;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.UserEntity;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.ProjectService;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.UserEntityService;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Components.NotificationComponents;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Project.AddProjectView;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Project.ProjectsView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.util.Optional;

@Route("/home")
@PageTitle("PV calculator")
@RolesAllowed({"USER", "ADMIN"})
public class MainView extends VerticalLayout {
    private final UserEntityService userEntityService;
    private final ProjectService projectService;

    public MainView(UserEntityService userEntityService, ProjectService projectService){
        this.userEntityService = userEntityService;
        this.projectService = projectService;
        addClassName("main-layout");
        getStyle().set("padding-top", "124px");

        Optional<UserEntity> userOpt = userEntityService.loadCurrentUserForVaadin();
        if (userOpt.isEmpty()) {
            NotificationComponents.showErrorMessage("Log in to access this resource");
            return;
        }
        UserEntity user = userOpt.get();

        VerticalLayout projectsLayout = myProjectsLayout(user);
        VerticalLayout createProjectLayout = createProjectLayout(user.getUserId());
        add(projectsLayout, createProjectLayout);
    }
    private VerticalLayout myProjectsLayout(UserEntity user){
        VerticalLayout projectsLayout = new VerticalLayout();
        Div myProjectsDiv = new Div("My projects");
        myProjectsDiv.addClassName("box");
        myProjectsDiv.getStyle().set("border", "3px solid darkblue");

        projectsLayout.add(myProjectsDiv);
        projectsLayout.addClickListener(e -> {
            UI.getCurrent().navigate(ProjectsView.class, user.getUserId());
        });
        return projectsLayout;
    }
    private VerticalLayout createProjectLayout(Long userId){
        VerticalLayout createProjectLayout = new VerticalLayout();
        Div myProjectsDiv = new Div("Create Project");
        myProjectsDiv.addClassName("box");
        myProjectsDiv.getStyle().set("border", "3px solid darkred");

        createProjectLayout.add(myProjectsDiv);
        createProjectLayout.addClickListener(e -> {
            UI.getCurrent().navigate(AddProjectView.class, userId);
        });
        return createProjectLayout;
    }
}
