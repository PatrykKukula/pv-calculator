package com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Components;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.RowDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.InstallationService;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Installation.InstallationUpdateView;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Project.ProjectView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class SingleInstallationLayout extends VerticalLayout {
    private final InstallationService installationService;
    private final ProjectDto project;
    private final InstallationDto installation;
    private final int CONVERT_W_TO_KW = 1000;

    public SingleInstallationLayout(InstallationService installationService, InstallationDto installation, ProjectDto project){
        this.installationService = installationService;
        this.project = project;
        this.installation = installation;

        add(singleInstallationLayout());
    }
    private VerticalLayout singleInstallationLayout(){
        VerticalLayout layout = new VerticalLayout();

        Icon delete = deleteIcon(installation.getInstallationId());
        Icon update = updateIcon(installation.getInstallationId());

        Span address = addressSpan(installation.getAddress());
        Div installationType = propertyDiv("Type: ", installation.getConstructionType().toString());
        Div installationPower = propertyDiv("Power: ", getInstallationPower(installation));
        Div modules = propertyDiv("Modules: ", getModules(installation));

        Span icons = new Span (update, delete);
        icons.getStyle().set("padding-left", "500px");
        address.add(icons);

        layout.add(address, installationType, installationPower, modules);
        layout.getStyle().set("align-items", "center").set("border", "2px solid green").set("box-shadow", "5px 5px 5px green").set("height", "100%");
        return layout;
    }
    private Div propertyDiv(String header, String value){
        Span headerSpan = new Span(header);
        headerSpan.getStyle().set("font-weight", "bold");
        return new Div(headerSpan, new Span(value));
    }
    private Span addressSpan(String value){
        Span addressSpan = new Span("Address: ");
        addressSpan.getStyle().set("font-weight", "bold");
        return new Span(addressSpan, new Span(value));
    }
    private String getInstallationPower(InstallationDto installation){
        return installation.getRows().stream()
                .mapToDouble(inst -> (double) inst.getModuleQuantity() * project.getModulePower()).sum() / CONVERT_W_TO_KW + " kW";
    }
    private String getModules(InstallationDto installation){
        return String.valueOf(installation.getRows().stream().mapToLong(RowDto::getModuleQuantity).sum());
    }
    private Icon deleteIcon(Long installationId){
        Icon icon = VaadinIcon.CLOSE.create();
        icon.getStyle().set("font-size", "10px").set("color", "red").set("cursor", "pointer");
        icon.addClickListener(e -> {
            installationService.removeInstallation(installationId);
            UI.getCurrent().navigate(ProjectView.class, project.getProjectId());
        });
        return icon;
    }
    private Icon updateIcon(Long installationId){
        Icon icon = VaadinIcon.BOOK.create();
        icon.getStyle().set("font-size", "10px").set("color", "blue").set("cursor", "pointer");
        icon.addClickListener(e -> UI.getCurrent().navigate(InstallationUpdateView.class, installationId));
        return icon;
    }
}
