package com.github.PatrykKukula.Photovoltaic.materials.calculator.View;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route("projects")
@PageTitle("Projects")
@RolesAllowed({"USER", "ADMIN"})
public class ProjectsView extends VerticalLayout {
}
