package pl.patrykkukula.DataPresentationFormatters;

import pl.patrykkukula.Model.ConstructionModel.DoubleThread;

public class DoubleThreadFormatter {

    public static String format(DoubleThread doubleThread){
        return  "Materiały konstrukcyjne: " + System.lineSeparator() +
                "Liczba paneli (jedna wartość oznacza jeden rząd): " + doubleThread.getInstallation().getRowsAndModules() + System.lineSeparator() +
                "Profil: " +  doubleThread.getProfile() + System.lineSeparator() +
                "Śruba dwugwintowa M10x250: " + doubleThread.getDoubleThreadScrew() + System.lineSeparator() +
                "Adapter montażowy: " + doubleThread.getAdapter() + System.lineSeparator() +
                "Łącznik profila: " + doubleThread.getProfileJoiner() + System.lineSeparator() +
                "Śruba M10x25: " + doubleThread.getHexagonScrew() + System.lineSeparator() +
                "Nakrętka M10 podkładkowa: " + doubleThread.getHexagonNut() + System.lineSeparator() +
                "Wpusty przesuwne: " + doubleThread.getSlidingKey() + System.lineSeparator() +
                "Śruby imbusowe: " + doubleThread.getAllenScrew() + System.lineSeparator() +
                "Klemy środkowe: " + doubleThread.getMidClamp() + System.lineSeparator() +
                "Klemy końcowe " + doubleThread.getInstallation().getModule().getFrame() + "mm: " + doubleThread.getEndClamp();
    }
}
