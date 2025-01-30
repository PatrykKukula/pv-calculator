package pl.patrykkukula.Utils;
import pl.patrykkukula.Model.ConstructionModel.*;
import pl.patrykkukula.Model.ConstructionModel.Abstract.AbstractConstructionModel;
import pl.patrykkukula.Model.ConstructionModel.Abstract.AbstractProfileConstructionModel;
import pl.patrykkukula.Model.Installation;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConstructionMapper {
    Map<String, Integer> materials = new LinkedHashMap<>();

    public  <T extends AbstractConstructionModel> Map<String, Integer> map(Installation installation, T type){
        if (type.getClass() == Trapeze.class) return mapToTrapeze(installation, (Trapeze)type);
        if (type.getClass() == DoubleThreaded.class) return mapToDoubleThread(installation, (DoubleThreaded) type);
        if (type.getClass() == FlatDoubleThreaded.class) return mapToFlatDoubleThread(installation, (FlatDoubleThreaded) type);
        if (type.getClass() == FlatThreadedRod.class) return mapToFlatThreadedRod(installation, (FlatThreadedRod) type);
        if (type.getClass() == VarioHook.class) return mapToVario(installation, (VarioHook) type);
        return null;
    }
    private Map<String, Integer> mapToTrapeze(Installation installation, Trapeze trapeze){
        materials.put("Mostek trapezowy", trapeze.getTrapeze());
        materials.put("Wkręt farmerski", trapeze.getSelfDrillingScrew());
        setBase(installation, trapeze);
        return materials;
    }
    private Map<String, Integer> mapToVario(Installation installation, VarioHook varioHook){
        setProfileBase(varioHook);
        materials.put("Hak Vario: ", varioHook.getVarioHook());
        materials.put("Wkręt ciesielski 8x80", varioHook.getWoodScrew());
        setBase(installation, varioHook);
        return materials;
    }
    private Map<String, Integer> mapToDoubleThread(Installation installation, DoubleThreaded doubleThreaded){
        setProfileBase(doubleThreaded);
        materials.put("Śruba Dwugwintowa M10x250", doubleThreaded.getDoubleThreadScrew());
        materials.put("Adapter montażowy", doubleThreaded.getAdapter());
        setBase(installation, doubleThreaded);
        return materials;
    }
    private Map<String, Integer> mapToFlatDoubleThread(Installation installation, FlatDoubleThreaded flatDoubleThreaded){
        setFlatBase(installation, flatDoubleThreaded);
        materials.put("Śruba Dwugwintowa M10x250", flatDoubleThreaded.getDoubleThreadScrew());
        setBase(installation, flatDoubleThreaded);
        return materials;
    }
    private Map<String, Integer> mapToFlatThreadedRod(Installation installation, FlatThreadedRod flatThreadedRod){
        setFlatBase(installation, flatThreadedRod);
        materials.put("Pręt gwintowany 30 cm", flatThreadedRod.getThreadedRod());
        materials.put("Podkładka epdm M10", flatThreadedRod.getEpdm());
        materials.put("Kotwa chemiczna", flatThreadedRod.getChemicalAnchor());
        materials.put("Koszyk do kotwy chemicznej", flatThreadedRod.getSleeve());
        setBase(installation, flatThreadedRod);
        return materials;
    }
    private <T extends AbstractConstructionModel> void setBase(Installation installation, T construction) {
        materials.put("Śruba imbusowa " + getAllenScrewLength(installation) +" mm", construction.getAllenScrew());
        materials.put("Wpust przesuwny", construction.getSlidingKey());
        materials.put("Klema końcowa " + getFrame(installation) + " mm", construction.getEndClamp());
        materials.put("Klema środkowa", construction.getMidClamp());
    }
    private <T extends AbstractProfileConstructionModel> void setProfileBase(T construction){
        materials.put("Profil al. 40x40", construction.getProfile());
        materials.put("Łącznik profila 40x40", construction.getProfileJoiner());
        materials.put("Śruba M10x25", construction.getHexagonScrew());
        materials.put("Nakrętka M10 podkładkowa", construction.getHexagonScrew());
    }

    private <T extends AbstractProfileConstructionModel> void setFlatBase(Installation installation, T construction){
        setProfileBase(construction);
        materials.put("Kątownik al. 40x40x30", construction.getAngleBar());

    }
    private int getAllenScrewLength(Installation installation){
        return installation.getModule().getFrame()-10;
    }
    private int getFrame(Installation installation){
        return installation.getModule().getFrame();
    }
}
