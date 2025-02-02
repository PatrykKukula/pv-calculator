package pl.patrykkukula.Builders;
import pl.patrykkukula.Model.Installation;
import pl.patrykkukula.Model.InstallationList;
import pl.patrykkukula.Utils.ConstructionMapper;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MaterialListBuilder {
    private final InstallationList installationList;
    private final Map<String, Integer> constructionMaterials = new LinkedHashMap<>();

    protected MaterialListBuilder(InstallationList installationList) {
        this.installationList = installationList;
    }
   protected Map<String, Integer> printMaterialList(){
        for (int i = 0; i<installationList.getInstallationList().size(); i++){
            Installation installation = installationList.getInstallationList().get(i);
            ConstructionMapper mapper = new ConstructionMapper();
            Map<String, Integer> map = mapper.map(installation, installation.getModel());
            Set<String> keys = map.keySet();
            for (String key : keys) {
                constructionMaterials.put(key, constructionMaterials.getOrDefault(key,0) + map.get(key));
            }
        }
        return constructionMaterials;
    }

//    public Map<String, Integer> calculateMaterials(){
//        installationList.getInstallationList().stream()
//                .map(installation -> {
//                    ConstructionMapper mapper = new ConstructionMapper();
//                    mapper.map(installation, installation.getModel(), installation)
//
//
//
//
//                })
//
//
//    }

}
