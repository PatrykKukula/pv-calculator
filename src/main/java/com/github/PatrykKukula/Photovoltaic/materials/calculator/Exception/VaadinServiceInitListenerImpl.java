package com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception;

import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VaadinServiceInitListenerImpl implements VaadinServiceInitListener {
    @Autowired
    private VaadinErrorHandler vaadinErrorHandler;

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addSessionInitListener(e -> {
           e.getSession().setErrorHandler(vaadinErrorHandler);
        });
    }
}
