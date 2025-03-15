package pl.patrykkukula.Builders;
import lombok.Getter;
import pl.patrykkukula.Model.Installation;
import pl.patrykkukula.Model.InstallationList;
import pl.patrykkukula.Utils.MaterialsUtils.ConstructionMapper;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class ConstructionMaterialsBuilder {
    private final InstallationList installationList;
    private final ConstructionMapper mapper = new ConstructionMapper();

    protected ConstructionMaterialsBuilder(InstallationList installationList) {
        this.installationList = installationList;
    }

    protected Map<String, Integer> buildConstructionMaterials(){
       Map<String, Integer> constructionMaterials = new LinkedHashMap<>();
            for (int i = 0; i < installationList.getInstallationList().size(); i++) {
                Installation installation = installationList.getInstallationList().get(i);
                Map<String, Integer> materialListForSingleInstallation = mapper.addConstructionMaterials(installation, installation.getModel());
                for (String material : materialListForSingleInstallation.keySet()) {
                    constructionMaterials.put(material, constructionMaterials.getOrDefault(material, 0) + materialListForSingleInstallation.get(material));
                }
            }
        return constructionMaterials;
    }
}

