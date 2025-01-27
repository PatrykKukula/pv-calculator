package pl.patrykkukula.Builders;

import pl.patrykkukula.Model.Installation;
import pl.patrykkukula.Model.InstallationList;
import pl.patrykkukula.Utils.ConstructionMapper;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ConstructionBuilder {
    InstallationList installationList;
    Map<String, Integer> materials = new LinkedHashMap<>();


    public ConstructionBuilder(InstallationList installationList) {
        this.installationList = installationList;
    }

    public Map<String, Integer> materials(){
        for (int i = 0; i<installationList.getInstallationList().size(); i++){
            Installation installation = installationList.getInstallationList().get(i);
            ConstructionMapper mapper = new ConstructionMapper();
            Map<String, Integer> map = mapper.map(installation, installation.getModel());
            Set<String> keys = map.keySet();
            for (String key : keys) {
                materials.put(key, materials.getOrDefault(key,0) + map.get(key));
            }
        }
        return materials;
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
