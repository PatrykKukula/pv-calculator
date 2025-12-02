package com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Project;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectRequestDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.ProjectService;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Components.PageButtons;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.PagingConstants.*;
import static com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Components.ProjectCommonComponents.titlesDiv;
import static com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Components.ProjectCommonComponents.valuesDiv;

@Route("/projects")
@PageTitle("PV Calculator - projects")
@RequiredArgsConstructor
@RolesAllowed({"USER", "ADMIN"})
@CssImport("./styles/common-styles.css")
public class ProjectsView extends VerticalLayout implements HasUrlParameter<Long>, BeforeEnterObserver {
    private final ProjectService projectService;
    private VerticalLayout projectsLayout = new VerticalLayout();
    private PageButtons<ProjectDto> pageButtons;
    private Page<ProjectDto> projects;

    @Override
    public void setParameter(BeforeEvent event, Long userId) {
        getStyle().set("width", "40%").set("height", "100%").set("display", "flex").set("align-items", "center")
                .set("margin", "auto").set("align-items", "center");

        projects = projectService.findAllProjects(new ProjectRequestDto(null, SORT_ASC, PAGE_NO, PAGE_SIZE));

        pageButtons = new PageButtons<>(projects, renderPage());

        renderPage().run();
        Button createProjectButton = createProjectButton(userId);
        add(createProjectButton);
        if (projects.getContent().isEmpty()){
            VerticalLayout layout = setNoProjectsLayout();
            addComponentAtIndex(0, layout);
        }
        else add(projectsLayout, pageButtons);

    }
    private Runnable renderPage(){
       return () -> {
           List<ProjectDto> projects = projectService.findAllProjects(new ProjectRequestDto(null, SORT_ASC, pageButtons.getCurrentPage(), PAGE_SIZE)).getContent();
           projectsLayout.removeAll();
           for (ProjectDto project : projects) {
               VerticalLayout layout = setSingleProjectLayout(project);
               projectsLayout.add(layout);
           }
       };
    }
    private VerticalLayout setNoProjectsLayout(){
        VerticalLayout layout = new VerticalLayout();
        Div div = new Div("You do not have any projects");
        div.getStyle().set("font-size", "24px").set("font-family", "georgia");
        layout.add(div);
        layout.setAlignItems(Alignment.CENTER);
        return layout;
    }
    private Button createProjectButton(Long userId){
        Button button = new Button("Create new project");
        button.addClickListener(e -> UI.getCurrent().navigate(AddProjectView.class, userId));
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return button;
    }
    private VerticalLayout setSingleProjectLayout(ProjectDto project){
        VerticalLayout layout = new VerticalLayout();
        layout.addClassName("white-background");
        Div title = getProjectTitle(project);
        layout.add(title);
        VerticalLayout projectDetails = getProjectDetails(project);
        layout.add(projectDetails);
        layout.getStyle().set("border", "2px solid black").set("box-shadow", "0 0 3px #ff0000 inset").set("padding", "10px 20px").set("cursor", "pointer");
        return layout;
    }
    private Div getProjectTitle(ProjectDto project){
        Div div = new Div(project.getTitle());
        Span createdAt = new Span(project.getCreatedAt());
        createdAt.getStyle().set("font-size", "10px");
        div.add(createdAt);
        div.getStyle().set("font-size", "24px").set("justify-content", "space-between").set("width", "100%").set("display", "flex");
        return div;
    }
    private VerticalLayout getProjectDetails(ProjectDto project){
        VerticalLayout layout = new VerticalLayout();
        Div tilteDiv = titlesDiv();
        Div valuesDiv = valuesDiv(project);
        HorizontalLayout horizontalLayout = new HorizontalLayout(tilteDiv, valuesDiv);
        layout.add(horizontalLayout);
        layout.setSpacing(false);
        layout.addClickListener(e -> UI.getCurrent().navigate(ProjectView.class, project.getProjectId()));
        return layout;
    }
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
    }
}
