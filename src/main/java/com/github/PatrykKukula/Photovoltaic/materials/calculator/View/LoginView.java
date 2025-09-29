package com.github.PatrykKukula.Photovoltaic.materials.calculator.View;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("/login")
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends LoginForm {
}
