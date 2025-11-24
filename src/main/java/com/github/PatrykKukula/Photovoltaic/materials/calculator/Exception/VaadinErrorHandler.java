package com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.ErrorEvent;
import com.vaadin.flow.server.ErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;

import static com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Components.NotificationComponents.showErrorMessage;

@Component
public class VaadinErrorHandler implements ErrorHandler {
    private static final Logger log = LoggerFactory.getLogger(VaadinErrorHandler.class);
    @Override
    public void error(ErrorEvent event) {
        Throwable throwable = event.getThrowable();
        log.info("Error thrown in Vaadin:{} ", throwable.getMessage());
        if (throwable instanceof AppException ex) {
            showErrorMessage(ex.getUserMessage());
        }

        else {
            showErrorMessage(throwable.getMessage());
            if (throwable instanceof BadCredentialsException || throwable instanceof AuthenticationException){
                UI.getCurrent().getPage().reload();
            }
        }
        throwable.printStackTrace();
    }
}
