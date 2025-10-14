package com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception;

import com.vaadin.flow.server.ErrorEvent;
import com.vaadin.flow.server.ErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
        else showErrorMessage(throwable.getMessage());
    }
}
