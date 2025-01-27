package pl.patrykkukula.DataPresentationFormatters;

import pl.patrykkukula.Model.ConstructionModel.FlatThreaderRod;

public class FlatThreadedRodFormatter {

    public static String format(FlatThreaderRod flatThreaderRod){
        return  "Materiały konstrukcyjne: " + System.lineSeparator() +
                "Liczba paneli (jedna wartość oznacza jeden rząd): " + flatThreaderRod.getInstallation().getRowsAndModules() + System.lineSeparator() +
                "Profil: " +  flatThreaderRod.getProfile() + System.lineSeparator() +
                "Pręt gwintowany M10 L=30 cm: " + flatThreaderRod.getThreadedRod() + System.lineSeparator() +
                "Kątownik al. 40x40x30: " + flatThreaderRod.getAngleBar() + System.lineSeparator() +
                "Łącznik profila: " + flatThreaderRod.getProfileJoiner() + System.lineSeparator() +
                "Śruba M10x25: " + flatThreaderRod.getHexagonScrew() + System.lineSeparator() +
                "Nakrętka M10 podkładkowa: " + flatThreaderRod.getHexagonNut() + System.lineSeparator() +
                "Podkładka EPDM M10: " + flatThreaderRod.getEpdm() + System.lineSeparator() +
                "Kotwa chemiczna: " + flatThreaderRod.getChemicalAnchor() + System.lineSeparator() +
                "Wpusty przesuwne: " + flatThreaderRod.getSlidingKey() + System.lineSeparator() +
                "Śruby imbusowe: " + flatThreaderRod.getAllenScrew() + System.lineSeparator() +
                "Klemy środkowe: " + flatThreaderRod.getMidClamp() + System.lineSeparator() +
                "Klemy końcowe " + flatThreaderRod.getInstallation().getModule().getFrame() + "mm: " + flatThreaderRod.getEndClamp();
    }
}
