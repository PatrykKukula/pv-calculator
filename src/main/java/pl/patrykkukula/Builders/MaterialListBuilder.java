package pl.patrykkukula.Builders;
import lombok.Getter;
import pl.patrykkukula.Model.Installation;
import pl.patrykkukula.Model.InstallationList;
import pl.patrykkukula.Utils.ConstructionMapper;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Getter
public class MaterialListBuilder {
    private final InstallationList installationList;
    private final Map<String, Integer> constructionMaterials = new LinkedHashMap<>();

    protected MaterialListBuilder(InstallationList installationList) {
        this.installationList = installationList;
    }

    protected Map<String, Integer> printMaterialList(){
        if (installationList.getInstallationList().isEmpty()) throw new RuntimeException("Lista instalacji jest pusta");

        for (int i = 0; i < installationList.getInstallationList().size(); i++) {
            Installation installation = installationList.getInstallationList().get(i);
            ConstructionMapper mapper = new ConstructionMapper();
            Map<String, Integer> materialList = mapper.printMaterials(installation, installation.getModel());
            Set<String> keys = materialList.keySet();
            for (String key : keys) {
                constructionMaterials.put(key, constructionMaterials.getOrDefault(key, 0) + materialList.get(key));
            }
        }
        return constructionMaterials;
    }
}

//    public Map<String, Integer> calculateMaterials(){
//        installationList.getInstallationList().stream()
//                .printMaterials(installation -> {
//                    ConstructionMapper mapper = new ConstructionMapper();
//                    mapper.printMaterials(installation, installation.getModel(), installation)
//
//
//
//
//                })
//
//
//    }


