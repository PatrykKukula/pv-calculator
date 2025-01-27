package pl.patrykkukula.DataPresentationFormatters;

import pl.patrykkukula.Model.ConstructionModel.Trapeze;

public class TrapezeFormatter {

    public static String format(Trapeze trapeze){
        return  "Materiały konstrukcyjne: " + System.lineSeparator() +
                "Liczba paneli (jedna wartość oznacza jeden rząd): " + trapeze.getInstallation().getRowsAndModules() + System.lineSeparator() +
                "Mostki trapezowe: " + trapeze.getTrapeze() + System.lineSeparator() +
                "Wkręty farmerskie: " + trapeze.getScrew() + System.lineSeparator() +
                "Wpusty przesuwne: " + trapeze.getSlidingKey() + System.lineSeparator() +
                "Śruby imbusowe: " + trapeze.getAllenScrew() + System.lineSeparator() +
                "Klemy środkowe: " + trapeze.getMidClamp() + System.lineSeparator() +
                "Klemy końcowe " + trapeze.getInstallation().getModule().getFrame() + "mm: " + trapeze.getEndClamp();
    }
}
