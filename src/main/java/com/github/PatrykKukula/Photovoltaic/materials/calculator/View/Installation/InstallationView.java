package com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Installation;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.InstallationMaterial.InstallationMaterialDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.InstallationService;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.MaterialService;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.ProjectService;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Components.SingleInstallationLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Route("/installation")
@PageTitle("PV calculator - installation")
@RolesAllowed({"USER", "ADMIN"})
@RequiredArgsConstructor
public class InstallationView extends VerticalLayout implements HasUrlParameter<Long> {
    private final InstallationService installationService;
    private final ProjectService projectService;
    private final MaterialService materialService;
    private final VerticalLayout constructionMaterialsLayout = new VerticalLayout();
    private final VerticalLayout electricalMaterialsLayout = new VerticalLayout();
    private List<InstallationMaterialDto> constructionMaterials;
    private List<InstallationMaterialDto> electricalMaterials;

    @Override
    public void setParameter(BeforeEvent event, Long installationId) {
        getStyle().set("width", "40%").set("margin", "auto");
        InstallationDto installation = installationService.findInstallationById(installationId);
        ProjectDto project = projectService.findProjectById(installation.getProjectId());

        SingleInstallationLayout singleInstallationLayout = new SingleInstallationLayout(installationService, installation, project, true);
        singleInstallationLayout.setSizeFull();

        constructionMaterials = materialService.fetchConstructionMaterialsForInstallation(installationId);
        electricalMaterials = materialService.fetchElectricalMaterialsForInstallation(installationId);

        setConstructionMaterialsLayout();
        setElectricalMaterialsLayout();
        HorizontalLayout materialsLayout = new HorizontalLayout(constructionMaterialsLayout, electricalMaterialsLayout);
        materialsLayout.getStyle().set("justify-content", "space-between").set("align-self", "center").set("width", "100%");

        add(singleInstallationLayout, materialsLayout);
    }
    private void setConstructionMaterialsLayout(){
        Div header = new Div("Construction materials");
        header.getStyle().set("font-weight", "bold").set("font-size", "22px").set("font-family", "georgia");
        constructionMaterialsLayout.add(header);
        constructionMaterialsLayout.getStyle().set("align-items", "start").set("gap", "0px");
        for (InstallationMaterialDto material : constructionMaterials){
            Span name = new Span(material.getName() + ": ");
            name.getStyle().set("font-weight", "bold");
            Span quantity = new Span(material.getQuantity().toString());
            quantity.getStyle().set("margin-left", "5px");
            Span unit = new Span(material.getUnit());
            HorizontalLayout materialLayout = new HorizontalLayout(name, quantity, unit);
            materialLayout.setSpacing(false);
            constructionMaterialsLayout.add(materialLayout);
        }
    }
    private void setElectricalMaterialsLayout(){
        Div header = new Div("Electrical materials");
        header.getStyle().set("font-weight", "bold").set("font-size", "22px").set("font-family", "georgia");
        electricalMaterialsLayout.add(header);
        electricalMaterialsLayout.getStyle().set("align-items", "start").set("gap", "0px").set("width", "900px");
        for (InstallationMaterialDto material : electricalMaterials){
            Span name = new Span(material.getName() + ": ");
            name.getStyle().set("font-weight", "bold");
            Span quantity = new Span(material.getQuantity().toString());
            quantity.getStyle().set("margin-left", "5px");
            Span unit = new Span(material.getUnit());
            HorizontalLayout materialLayout = new HorizontalLayout(name, quantity, unit);
            materialLayout.setSpacing(false);
            electricalMaterialsLayout.add(materialLayout);
        }
    }
}
