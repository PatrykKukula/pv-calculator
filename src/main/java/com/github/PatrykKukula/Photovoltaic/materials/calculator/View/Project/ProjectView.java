package com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Project;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationsRequestDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.UserEntity;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.InstallationService;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.ProjectService;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.UserEntityService;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.View.AppLayoutImpl;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Components.FileExportLayout;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Components.PageButtons;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Components.SingleInstallationLayout;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Installation.AddInstallationView;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Installation.InstallationView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.PagingConstants.*;
import static com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Components.ProjectCommonComponents.titlesDiv;
import static com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Components.ProjectCommonComponents.valuesDiv;

@Slf4j
@Route(value = "/project", layout = AppLayoutImpl.class)
@PageTitle("PV calculator")
@RolesAllowed({"USER", "ADMIN"})
@RequiredArgsConstructor
public class ProjectView extends VerticalLayout implements HasUrlParameter<Long> {
    private final ProjectService projectService;
    private final InstallationService installationService;
    private ProjectDto project;
    private final UserEntityService userEntityService;
    private final VerticalLayout installationsLayout = new VerticalLayout();
    private Page<InstallationDto> installations;
    private PageButtons<InstallationDto> pageButtons;

    @Override
    public void setParameter(BeforeEvent event, Long projectId) {
        removeAll();

        getStyle().set("width", "40%").set("display", "flex").set("align-items", "center")
                .set("margin", "auto").set("border-left", "2px solid black").set("border-right", "2px solid black").set("min-height", "100vh");

        FileExportLayout fileExportLayout = new FileExportLayout(projectId, projectService);

        project = projectService.findProjectById(projectId);

            installations = installationService.findAllInstallationsForProject(projectId, InstallationsRequestDto.builder().pageNo(PAGE_NO).pageSize(PAGE_SIZE).sortDirection(SORT_DESC).build());
            pageButtons = new PageButtons<>(installations, renderInstallations());
            renderInstallations().run();

            VerticalLayout left = new VerticalLayout(projectDetailsHeader(), projectDetailsLayout());
            VerticalLayout right = new VerticalLayout(moduleDataHeader(), moduleData());
            HorizontalLayout detailsLayout = new HorizontalLayout(left, right);

        add(projectTitle(), detailsLayout, buttonsDiv(), installationsHeader(),
                    addInstalationButton(), fileExportLayout, installationsLayout, pageButtons);
    }

    private HorizontalLayout projectDetailsLayout(){
        HorizontalLayout detailsLayout = new HorizontalLayout();
        detailsLayout.getStyle().set("width", "100%").set("padding", "5px 10px").set("line-height", "300%").set("font-size", "16px")
                .set("font-family", "georgia").set("justify-content", "center");

        Div titlesDiv = titlesDiv();
        Div valuesDiv = valuesDiv(project);

        Div installationsDataTitles = installationsDataTitles();
        Div installationsDataValues = installationsDataValues();

        titlesDiv.add(installationsDataTitles);
        valuesDiv.add(installationsDataValues);

        detailsLayout.add(titlesDiv, valuesDiv);
        return detailsLayout;
    }
    private Runnable renderInstallations(){
        return () -> {
            installationsLayout.removeAll();
            List<InstallationDto> installations = installationService.findAllInstallationsForProject(project.getProjectId(), new InstallationsRequestDto(SORT_ASC, pageButtons.getCurrentPage(), PAGE_SIZE)).getContent();
            for (InstallationDto installation : installations){
                VerticalLayout singleInstallationLayout = new SingleInstallationLayout(installationService, installation, project, false);
                singleInstallationLayout.addClickListener(e -> UI.getCurrent().navigate(InstallationView.class, installation.getInstallationId()));
                singleInstallationLayout.getStyle().set("cursor", "pointer");
                installationsLayout.add(singleInstallationLayout);
            }
        };
    }
    private Div buttonsDiv(){
        Div div = new Div();
        Button editButton = editButton();
        Button removeButton = removeButton();
        div.add(editButton, removeButton);
        div.getStyle().set("display", "flex").set("justify-content", "space-around").set("width", "100%");

        return div;
    }
    private Div installationsDataTitles(){
        Div div = new Div();
        Div installations = new Div("Installations: ");
        Div power = new Div("Total power: ");

        div.add(installations, power);
        div.getStyle().set("font-weight", "bold");
        return div;
    }
    private Div installationsDataValues(){
        Div div = new Div();

        Integer count = projectService.getInstallationCountForProject(project.getProjectId());
        String totalPower = String.format("%.2f", projectService.getTotalPowerForProject(project.getProjectId())) + " kW";

        div.add(new Div(count.toString()), new Div(totalPower));
        return div;
    }
    private Div moduleDataTitles(){
        Div div = new Div();
        Div power= new Div("Module power: ");
        Div length = new Div("Module length: ");
        Div width = new Div("Module width: ");
        Div frame = new Div("Module frame: ");

        div.add(power, length, width, frame);
        div.getStyle().set("font-weight", "bold");
        return div;
    }
    private Div moduleDataValues(){
        Div div = new Div();
        Div power = new Div(project.getModulePower().toString() + " W");
        Div length = new Div(project.getModuleLength().toString() + " mm");
        Div width = new Div(project.getModuleWidth().toString() + " mm");
        Div frame = new Div(project.getModuleFrame().toString() + " mm");

        div.add(power, length, width, frame);
        return div;
    }
    private HorizontalLayout moduleData(){
        HorizontalLayout moduleData = new HorizontalLayout(moduleDataTitles(), moduleDataValues());
        moduleData.getStyle().set("width", "100%").set("padding", "5px 10px").set("line-height", "300%").set("font-size", "16px")
                .set("font-family", "georgia");
        return moduleData;
    }
    private Div projectTitle(){
        Div title = new Div(project.getTitle());
        title.getStyle().set("font-size", "36px").set("font-family", "georgia").set("font-weight", "bold").set("letter-spacing", "3px");
        return title;
    }
    private Div projectDetailsHeader(){
        Div header = new Div("Project details");
        header.getStyle().set("font-size", "24px").set("font-family", "georgia").set("margin", "auto");
        return header;
    }
    private Div moduleDataHeader(){
        Div header = new Div("Module details");
        header.getStyle().set("font-size", "24px").set("font-family", "georgia").set("align-self", "center");
        return header;
    }
    private Div installationsHeader(){
        Div header = new Div("Installations");
        header.getStyle().set("font-size", "24px").set("font-family", "georgia").set("margin-top", "30px");
        return header;
    }
    private Button editButton(){
        Button button = new Button("Edit project ", VaadinIcon.EDIT.create());
        button.addClickListener(e -> UI.getCurrent().navigate(ProjectUpdateView.class, project.getProjectId()));

        return button;
    }
    private Button removeButton(){
        Button button = new Button("Remove project", VaadinIcon.FILE_REMOVE.create());
        button.addThemeVariants(ButtonVariant.LUMO_ERROR);
        button.addClickListener(e -> {
            try{
                Optional<UserEntity> user = userEntityService.loadCurrentUserForVaadin();
                if (user.isPresent()) {
                    projectService.removeProject(project.getProjectId());
                    UI.getCurrent().navigate(ProjectsView.class, user.get().getUserId());
                }
            }
            catch (Exception ex){
                Notification.show("An error has occurred %s".formatted(ex.getMessage()), 7000, Notification.Position.MIDDLE);
            }
        });
        return button;
    }
    private Button addInstalationButton(){
        Button button = new Button("Add installation", VaadinIcon.PLUS.create());
        button.addClickListener(e ->{
            UI.getCurrent().navigate(AddInstallationView.class, project.getProjectId());
        });
        return button;
    }
}
