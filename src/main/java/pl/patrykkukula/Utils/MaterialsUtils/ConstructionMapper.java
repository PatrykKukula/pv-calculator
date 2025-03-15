package pl.patrykkukula.Utils.MaterialsUtils;
import pl.patrykkukula.Model.ConstructionModel.Abstract.AbstractConstructionModel;
import pl.patrykkukula.Model.ConstructionModel.Abstract.AbstractProfileConstructionModel;
import pl.patrykkukula.Model.ConstructionModel.*;
import pl.patrykkukula.Model.Installation;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;
import static pl.patrykkukula.Constants.ConstructionConstants.ALLEN_SCREW_LENGTH_MOD;

public class ConstructionMapper {
    private final Map<Class<? extends AbstractConstructionModel>, BiFunction<Installation, AbstractConstructionModel,
            Map<String, Integer>>> constructionMapper = new HashMap<>();

        public ConstructionMapper() {
            constructionMapper.put(Trapeze.class,
                    (installation, model) -> mapToTrapeze(installation,(Trapeze)model));
            constructionMapper.put(DoubleThreaded.class,
                    (installation, model) -> mapToDoubleThreaded(installation,(DoubleThreaded)model));
            constructionMapper.put(FlatDoubleThreaded.class,
                    (installation, model) -> mapToFlatDoubleThreaded(installation,(FlatDoubleThreaded) model));
            constructionMapper.put(FlatThreadedRod.class,
                    (installation, model) -> mapToFlatThreadedRod(installation,(FlatThreadedRod) model));
            constructionMapper.put(VarioHook.class,
                    (installation, model) -> mapToVario(installation,(VarioHook) model));
        }

    public Map<String, Integer> addConstructionMaterials(Installation installation, AbstractConstructionModel model) {
        BiFunction<Installation, AbstractConstructionModel, Map<String, Integer>> function = constructionMapper.get(installation.getModel().getClass());
        return function.apply(installation, model);
    }
    private Map<String, Integer> mapToTrapeze(Installation installation, Trapeze trapeze){
        Map<String, Integer> materials = new LinkedHashMap<>();
        materials.put("Mostek trapezowy", trapeze.getTrapeze());
        materials.put("Wkręt farmerski", trapeze.getSelfDrillingScrew());
        setBase(materials, installation, trapeze);
        return materials;
    }
    private Map<String, Integer> mapToVario(Installation installation, VarioHook varioHook){
        Map<String, Integer> materials = new LinkedHashMap<>();
        setProfileBase(materials, varioHook);
        materials.put("Hak Vario: ", varioHook.getVarioHook());
        materials.put("Wkręt ciesielski 8x80", varioHook.getWoodScrew());
        setBase(materials, installation, varioHook);
        return materials;
    }
    private Map<String, Integer> mapToDoubleThreaded(Installation installation, DoubleThreaded doubleThreaded){
        Map<String, Integer> materials = new LinkedHashMap<>();
        setProfileBase(materials, doubleThreaded);
        materials.put("Śruba Dwugwintowa M10x250", doubleThreaded.getDoubleThreadedScrew());
        materials.put("Adapter montażowy", doubleThreaded.getAdapter());
        setBase(materials, installation, doubleThreaded);
        return materials;
    }
    private Map<String, Integer> mapToFlatDoubleThreaded(Installation installation, FlatDoubleThreaded flatDoubleThreaded){
        Map<String, Integer> materials = new LinkedHashMap<>();
        setFlatBase(materials, flatDoubleThreaded);
        materials.put("Śruba Dwugwintowa M10x250", flatDoubleThreaded.getDoubleThreadedScrew());
        setBase(materials, installation, flatDoubleThreaded);
        return materials;
    }
    private Map<String, Integer> mapToFlatThreadedRod(Installation installation, FlatThreadedRod flatThreadedRod){
        Map<String, Integer> materials = new LinkedHashMap<>();
        setFlatBase(materials, flatThreadedRod);
        materials.put("Pręt gwintowany 30 cm", flatThreadedRod.getThreadedRod());
        materials.put("Podkładka epdm M10", flatThreadedRod.getEpdm());
        materials.put("Kotwa chemiczna", flatThreadedRod.getChemicalAnchor());
        materials.put("Koszyk do kotwy chemicznej", flatThreadedRod.getSleeve());
        setBase(materials, installation, flatThreadedRod);
        return materials;
    }
    private <T extends AbstractProfileConstructionModel> void setFlatBase(Map<String, Integer> materials, T construction){
        setProfileBase(materials, construction);
        materials.put("Kątownik al. 40x40x30", construction.getAngleBar());
    }
    private <T extends AbstractProfileConstructionModel> void setProfileBase(Map<String, Integer> materials, T construction){
        materials.put("Profil al. 40x40", construction.getProfile());
        materials.put("Łącznik profila 40x40", construction.getProfileJoiner());
        materials.put("Śruba M10x25", construction.getHexagonScrew());
        materials.put("Nakrętka M10 podkładkowa", construction.getHexagonScrew());
    }
    private <T extends AbstractConstructionModel> void setBase(Map<String, Integer> materials, Installation installation, T construction) {
        materials.put("Śruba imbusowa " + getAllenScrewLength(installation) +" mm", construction.getAllenScrew());
        materials.put("Wpust przesuwny", construction.getSlidingKey());
        materials.put("Klema końcowa " + getFrame(installation) + " mm", construction.getEndClamp());
        materials.put("Klema środkowa", construction.getMidClamp());
    }
    private int getAllenScrewLength(Installation installation){
        return installation.getModule().getFrame() - ALLEN_SCREW_LENGTH_MOD;
    }
    private int getFrame(Installation installation){
        return installation.getModule().getFrame();
    }
}
