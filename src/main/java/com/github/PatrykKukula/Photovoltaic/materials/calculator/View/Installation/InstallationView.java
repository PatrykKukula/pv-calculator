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
    private List<InstallationMaterialDto> constructionMaterials;

    @Override
    public void setParameter(BeforeEvent event, Long installationId) {
        getStyle().set("width", "40%").set("margin", "auto");
        InstallationDto installation = installationService.findInstallationById(installationId);
        ProjectDto project = projectService.findProjectById(installation.getProjectId());

        SingleInstallationLayout singleInstallationLayout = new SingleInstallationLayout(installationService, installation, project);
        singleInstallationLayout.setSizeFull();

        constructionMaterials = materialService.fetchConstructionMaterialsForInstallation(installationId);

        addConstructionMaterials();

        add(singleInstallationLayout, constructionMaterialsLayout);
    }
    private void addConstructionMaterials(){
        Div header = new Div("Construction materials");
        header.getStyle().set("font-weight", "bold").set("font-size", "22px").set("font-family", "georgia");
        constructionMaterialsLayout.add(header);
        constructionMaterialsLayout.getStyle().set("align-items", "center");
        for (InstallationMaterialDto material : constructionMaterials){
            Span name = new Span(material.getName());
            Span quantity = new Span(material.getQuantity().toString());
            constructionMaterialsLayout.add(name, quantity);
        }
    }

}
