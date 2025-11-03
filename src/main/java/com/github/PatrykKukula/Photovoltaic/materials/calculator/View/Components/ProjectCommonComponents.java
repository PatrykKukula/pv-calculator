package com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Components;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectDto;
import com.vaadin.flow.component.html.Div;

public class ProjectCommonComponents {
    public static Div titlesDiv() {
        Div investorDiv = new Div("Investor" + ": ");
        Div countryDiv = new Div("Country" + ": ");
        Div voivodeshipDiv = new Div("Voivodeship" + ": ");
        Div cityDiv = new Div("City" + ": ");
        Div div = new Div(investorDiv, countryDiv, voivodeshipDiv, cityDiv);
        div.getStyle().set("font-weight", "bold");
        return div;
    }
    public static Div valuesDiv(ProjectDto project) {
        Div investorDiv = new Div(project.getInvestor());
        Div countryDiv = new Div(project.getCountry());
        Div voivodeshipDiv = new Div(project.getVoivodeship());
        Div cityDiv = new Div(project.getCity());
        Div div = new Div(investorDiv, countryDiv, voivodeshipDiv, cityDiv);
        div.getStyle().set("padding-left", "10px");
        return div;
    }
}
