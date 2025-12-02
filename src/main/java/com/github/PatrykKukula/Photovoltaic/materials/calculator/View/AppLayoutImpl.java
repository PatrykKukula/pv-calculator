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
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.csrf.CsrfToken;

import java.util.Optional;

import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ImageConstants.MAIN_IMAGE_1;

@Slf4j
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

        addBackgroundImageWithGradient();
        addToNavbar(container);
    }
    private Span myProjectsTab(){
        Span span = new Span("My projects");
        span.addClassName("tab");
        span.addClickListener(e -> {
            if (UI.getCurrent().getCurrentView() instanceof ProjectsView) UI.getCurrent().getPage().reload();
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
    private void addBackgroundImageWithGradient() {
        Image backgroundImage = new Image(MAIN_IMAGE_1, "Background");
        backgroundImage.getStyle()
                .set("position", "absolute")
                .set("top", "0")
                .set("left", "0")
                .set("width", "100%")
                .set("height", "100%")
                .set("object-fit", "cover")
                .set("object-position", "center right")
                .set("z-index", "-1")
                .set("pointer-events", "none");

        Div gradientOverlay = new Div();
        gradientOverlay.getStyle()
                .set("position", "absolute")
                .set("top", "0")
                .set("left", "0")
                .set("width", "100%")
                .set("height", "100%")
                .set("background", "linear-gradient(to right, rgba(255, 255, 255, 1) 0%, rgba(255, 255, 255, 0.7) 30%, rgba(255, 255, 255, 0) 100%)")
                .set("z-index", "-1")
                .set("pointer-events", "none");

        getElement().insertChild(0, backgroundImage.getElement());
        getElement().insertChild(1, gradientOverlay.getElement());
    }
}
