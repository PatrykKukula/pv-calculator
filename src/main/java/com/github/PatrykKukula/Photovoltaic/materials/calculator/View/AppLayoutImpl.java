package com.github.PatrykKukula.Photovoltaic.materials.calculator.View;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.UserEntity;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.UserEntityService;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Project.AddProjectView;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Project.ProjectsView;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.web.csrf.CsrfToken;

import java.util.Optional;

@Layout
@AnonymousAllowed
@CssImport("./styles/app-layout-styles.css")
public class AppLayoutImpl extends AppLayout {
    private final UserEntityService userEntityService;
    private UserEntity user;
    private Html logoutForm;

    public AppLayoutImpl(UserEntityService userEntityService){
        this.userEntityService = userEntityService;
        setupLogout();

        HorizontalLayout container = new HorizontalLayout();
        container.setSizeFull();
        container.addClassName("container");

        Optional<UserEntity> userOpt = userEntityService.loadCurrentUserForVaadin();
        if (userOpt.isEmpty()) {
            container.addToEnd(loginTab());
        }
        if (userOpt.isPresent()){
            user = userOpt.get();
            container.add(new Span(myProjectsTab(), createProjectTab()), logoutTab());
        }

        addToNavbar(container);
    }
    private Span myProjectsTab(){
        Span span = new Span("My projects");
        span.addClassName("tab");
        span.addClickListener(e -> {
            if (UI.getCurrent().getCurrentView().isAttached()) UI.getCurrent().getPage().reload();
            else UI.getCurrent().navigate(ProjectsView.class, user.getUserId());
        });
        return span;
    }
    private Span createProjectTab(){
        Span span = new Span("Create project");
        span.addClassName("tab");
        span.addClickListener(e -> UI.getCurrent().navigate(AddProjectView.class, user.getUserId()));
        return span;
    }
    private Div loginTab(){
        Div div = new Div("Login");
        div.addClassName("tab");
        div.addClickListener(e -> UI.getCurrent().navigate(LoginView.class));
        return div;
    }
    private Div logoutTab(){
        setupLogout();
        Div div = new Div(logoutForm);
        div.addClassName("tab");
        return div;
    }
    private void setupLogout(){
        CsrfToken csrf = (CsrfToken) VaadinServletRequest.getCurrent().getAttribute(CsrfToken.class.getName());
        logoutForm = new Html("""
                <form id="logoutForm" method="post" action="/logout">
                    <input type="hidden" name="%s" value="%s"/>
                    <button type="submit" style="all: unset;">Logout</button>
                </form>
                """.formatted(csrf.getParameterName(), csrf.getToken()));
        logoutForm.setClassName("dropdown-tab");
    }
}
