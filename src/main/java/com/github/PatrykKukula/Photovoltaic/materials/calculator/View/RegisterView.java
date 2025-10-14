package com.github.PatrykKukula.Photovoltaic.materials.calculator.View;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.User.RegisterDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.UserEntityService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.validator.BeanValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Route("register")
@PageTitle("PV calculator - Register")
@AnonymousAllowed
@CssImport("./styles/common-styles.css")
public class RegisterView extends FormLayout {
    private final UserEntityService userEntityService;
    private BeanValidationBinder<RegisterDto> binder = new BeanValidationBinder<>(RegisterDto.class);
    private RegisterDto registerDto = new RegisterDto();

    public RegisterView(UserEntityService userEntityService){
        this.userEntityService = userEntityService;
        getStyle().set("width", "17%").set("margin", "auto").set("height", "100%").set("padding-top", "60px");

        TextField usernameField = usernameField();
        PasswordField passwordField = passwordField();

        Div div = registerDiv();
        Button registerButton = registerButton();

        setResponsiveSteps(new ResponsiveStep("0px", 1));
        binder.setBean(registerDto);

        add(div, usernameField, passwordField, registerButton);
    }
    private TextField usernameField(){
        TextField username = new TextField("Username");
        username.setAutofocus(true);
        binder.forField(username)
                .withValidator(new BeanValidator(RegisterDto.class, "username"))
                .bind("username");
        return username;
    }
    private PasswordField passwordField(){
        PasswordField passwordField = new PasswordField("Password");
        passwordField.setRevealButtonVisible(true);
        binder.forField(passwordField)
                .withValidator(new BeanValidator(RegisterDto.class, "password"))
                .bind("password");
        return passwordField;
    }
    private Div registerDiv(){
        Div div = new Div("Register");
        div.getStyle().set("font-size","26px");
        return div;
    }
    private Button registerButton(){
        Button button = new Button("Register");
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        button.addClickListener(e -> {
            if (binder.validate().isOk()){
                RegisterDto registerData = binder.getBean();
                boolean registered = userEntityService.register(registerData);
                if (registered){
                    Notification.show("Registered successfully", 5000, Notification.Position.MIDDLE);
                    UI.getCurrent().navigate(LandingPage.class);
                }
            }
            });
        return button;
    }
}

