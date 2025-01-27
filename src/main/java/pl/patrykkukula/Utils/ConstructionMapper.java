package pl.patrykkukula.Utils;

import pl.patrykkukula.Model.ConstructionModel.Abstract.AbstractConstructionModel;
import pl.patrykkukula.Model.ConstructionModel.Abstract.AbstractFlatConstructionModel;
import pl.patrykkukula.Model.ConstructionModel.DoubleThread;
import pl.patrykkukula.Model.ConstructionModel.FlatDoubleThread;
import pl.patrykkukula.Model.ConstructionModel.Trapeze;
import pl.patrykkukula.Model.Installation;

import java.util.LinkedHashMap;
import java.util.Map;

public class ConstructionMapper {
    Map<String, Integer> materials = new LinkedHashMap<>();

    public  <T extends AbstractConstructionModel> Map<String, Integer> map(Installation installation, T type){
        if (type.getClass() == Trapeze.class) return mapToTrapeze(installation, (Trapeze)type);
        if (type.getClass() == DoubleThread.class) return mapToDoubleThread(installation, (DoubleThread) type);
        if (type.getClass() == FlatDoubleThread.class) return mapToFlatDoubleThread(installation, (FlatDoubleThread) type);
        return null;
    }
    private Map<String, Integer> mapToTrapeze(Installation installation, Trapeze trapeze){
        materials.put("Mostek trapezowy", trapeze.getTrapeze());
        materials.put("Wkręt farmerski", trapeze.getScrew());
        setBase(installation, trapeze);
        return materials;
    }
    private Map<String, Integer> mapToDoubleThread(Installation installation, DoubleThread doubleThread){
        materials.put("Profil al. 40x40", doubleThread.getProfile());
        materials.put("Śruba Dwugwintowa M10x250", doubleThread.getDoubleThreadScrew());
        materials.put("Adapter montażowy", doubleThread.getAdapter());
        materials.put("Łącznik profila 40x40", doubleThread.getProfileJoiner());
        materials.put("Śruba M10x25", doubleThread.getHexagonScrew());
        materials.put("Nakrętka M10 podkładkowa", doubleThread.getHexagonScrew());
        materials.put("Śruba imbusowa" + getAllenScrewLenght(installation) +" mm", doubleThread.getAllenScrew());
        materials.put("Wpust przesuwny", doubleThread.getSlidingKey());
        materials.put("Klema końcowa " + getFrame(installation) + " mm", doubleThread.getEndClamp());
        materials.put("Klema środkowa", doubleThread.getMidClamp());
        return materials;
    }
    private Map<String, Integer> mapToFlatDoubleThread(Installation installation, FlatDoubleThread flatDoubleThread){
        setFlatBase(installation, flatDoubleThread);
        materials.put("Śruba Dwugwintowa M10x250", flatDoubleThread.getDoubleThreadScrew());
        setBase(installation,flatDoubleThread);
        return materials;
    }
    private <T extends AbstractConstructionModel> void setBase(Installation installation, T construction) {
        materials.put("Śruba imbusowa" + getAllenScrewLenght(installation) +" mm", construction.getAllenScrew());
        materials.put("Wpust przesuwny", construction.getSlidingKey());
        materials.put("Klema końcowa " + getFrame(installation) + " mm", construction.getEndClamp());
        materials.put("Klema środkowa", construction.getMidClamp());
    }
    private <T extends AbstractFlatConstructionModel> void setProfileBase(Installation installation, T construction){
        materials.put("Profil al. 40x40", construction.getProfile());
        materials.put("Łącznik profila 40x40", construction.getProfileJoiner());
        materials.put("Śruba M10x25", construction.getHexagonScrew());
        materials.put("Nakrętka M10 podkładkowa", construction.getHexagonScrew());
    }

    private <T extends AbstractFlatConstructionModel> void setFlatBase(Installation installation, T construction){
        setProfileBase(installation,construction);
        materials.put("Kątownik al. 40x40x30", construction.getAngleBar());

    }

    private int getAllenScrewLenght(Installation installation){
        return installation.getModule().getFrame()-10;
    }
    private int getFrame(Installation installation){
        return installation.getModule().getFrame();
    }
}
