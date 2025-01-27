package pl.patrykkukula.DataPresentationFormatters;

import pl.patrykkukula.Model.ConstructionModel.FlatDoubleThread;

public class FlatDoubleThreadFormatter {

    public static String format(FlatDoubleThread flatDoubleThread){
        return  "Materiały konstrukcyjne: " + System.lineSeparator() +
                "Liczba paneli (jedna wartość oznacza jeden rząd): " + flatDoubleThread.getInstallation().getRowsAndModules() + System.lineSeparator() +
                "Profil: " +  flatDoubleThread.getProfile() + System.lineSeparator() +
                "Śruba dwugwintowa M10x250: " + flatDoubleThread.getDoubleThreadScrew() + System.lineSeparator() +
                "Kątownik al. 40x40x30: " + flatDoubleThread.getAngleBar() + System.lineSeparator() +
                "Śruba M10x25: " + flatDoubleThread.getHexagonScrew() + System.lineSeparator() +
                "Nakrętka M10 podkładkowa: " + flatDoubleThread.getHexagonNut() + System.lineSeparator() +
                "Wpusty przesuwne: " + flatDoubleThread.getSlidingKey() + System.lineSeparator() +
                "Śruby imbusowe: " + flatDoubleThread.getAllenScrew() + System.lineSeparator() +
                "Klemy środkowe: " + flatDoubleThread.getMidClamp() + System.lineSeparator() +
                "Klemy końcowe " + flatDoubleThread.getInstallation().getModule().getFrame() + "mm: " + flatDoubleThread.getEndClamp();
    }
}
