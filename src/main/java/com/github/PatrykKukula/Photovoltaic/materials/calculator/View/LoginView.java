package com.github.PatrykKukula.Photovoltaic.materials.calculator.View;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.User.LoginDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Security.UserDetailsServiceImpl;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.UserEntityService;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Route("/login")
@PageTitle("PV Calculator - Login")
@AnonymousAllowed
@CssImport("./styles/common-styles.css")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {
    private final UserEntityService userService;
    private final UserDetailsServiceImpl userDetailsService;
    private final LoginForm loginForm;

    public LoginView(UserEntityService userService, UserDetailsServiceImpl userDetailsService){
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        loginForm = new LoginForm();
        loginForm.setForgotPasswordButtonVisible(false);
        loginForm.addLoginListener(new LoginListener(userService));

        Button registerButton = registerButton();

        VerticalLayout layout = new VerticalLayout(loginForm, registerButton);
        layout.setSpacing(false);
        layout.addClassName("main-layout");
        add(layout);
    }
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (!userService.isUserAnonymous()) {
            beforeEnterEvent.forwardTo(MainView.class);
        }
    }
    private Button registerButton(){
        Button button = new Button("Don't have account? Click to register.");
        button.addClickListener(event -> UI.getCurrent().navigate(RegisterView.class));
        button.getStyle().set("padding", "10px").set("margin", "0");

        return button;
    }
}
@Slf4j
class LoginListener implements ComponentEventListener<AbstractLogin.LoginEvent>{
    private final UserEntityService userEntityService;
    public LoginListener(UserEntityService userEntityService){
        this.userEntityService = userEntityService;
    }
    @Override
    public void onComponentEvent(AbstractLogin.LoginEvent event) {
            boolean login = userEntityService.login(new LoginDto(event.getUsername(), event.getPassword()));
            UI.getCurrent().getPage().reload();
            if (login) {
                UI.getCurrent().navigate(MainView.class);
            }
    }
}
