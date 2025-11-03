package com.github.PatrykKukula.Photovoltaic.materials.calculator.View;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.UserEntityService;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Route("")
@PageTitle("PV Calculator")
@AnonymousAllowed
@CssImport("./styles/common-styles.css")
public class LandingPage extends VerticalLayout implements BeforeEnterObserver {
    private final UserEntityService userEntityService;

    public LandingPage(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
            addClassName("main-layout");
            VerticalLayout mainLayout = mainLayout();
            Div mainDiv = mainDiv();
            add(mainLayout, mainDiv);
    }
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if(!userEntityService.isUserAnonymous()) event.rerouteTo(MainView.class);
    }
    private VerticalLayout mainLayout(){
        VerticalLayout layout = new VerticalLayout();

        Div div = new Div("Welcome to PV calculator! \n Speed up process of creating material lists for you photovoltaic installations! \n" +
                "Have all your projects in one place! ");
        div.getStyle().set("font-size", "36px").set("white-space", "pre-line").set("text-align", "center");

        layout.add(div);
        layout.getStyle().set("align-items", "center");
        return layout;
    }
    private Div mainDiv(){
        Div div = new Div();
        Anchor login = new Anchor("/login", "Login ");
        Anchor register = new Anchor("/register", " Register ");
        Span or = new Span("or");
        Span span = new Span("and try it out!");
        div.add(login, or, register, span);
        div.getStyle().set("font-size", "24px");

        return div;
    }
}
