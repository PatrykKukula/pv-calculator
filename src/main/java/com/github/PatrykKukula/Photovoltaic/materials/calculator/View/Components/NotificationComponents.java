package com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Components;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public class NotificationComponents {
    private NotificationComponents(){};

    public static void showErrorMessage(String message){
        Notification.show(message, 5000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR);
    }
}
