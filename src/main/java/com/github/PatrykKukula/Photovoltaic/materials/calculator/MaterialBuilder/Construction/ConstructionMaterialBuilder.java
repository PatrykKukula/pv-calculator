package com.github.PatrykKukula.Photovoltaic.materials.calculator.MaterialBuilder.Construction;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ConstructionType;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Installation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.InstallationMaterial;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.MaterialService;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ConstructionMaterialConstants.ALLEN_SCREW_LENGTH_MOD;

@Slf4j
public class ConstructionMaterialBuilder {
    private final MaterialService constructionMaterialService;
    private final ConstructionMaterialCalculator constructionMaterialCalculator;
    private final Map<ConstructionType, Function<Installation, List<InstallationMaterial>>> constructionMap;
    private final Installation installation;
    private final ProjectDto project;

    /**
     * Constructor to put available ConstructionType and corresponding method to create InstallationMaterial List.
     * @param installation - installation to count material for
     * @param constructionMaterialService - construction material service
     * @param constructionMaterialCalculator - construction material calculator
     * @param project - ProjectDto that installation belongs to. It is needed due to the information about the modules
     */
    public ConstructionMaterialBuilder(Installation installation, MaterialService constructionMaterialService,
                                       ConstructionMaterialCalculator constructionMaterialCalculator, ProjectDto project){
        this.constructionMaterialService = constructionMaterialService;
        this.constructionMaterialCalculator = constructionMaterialCalculator;
        this.project = project;
        this.constructionMap = new HashMap<>();
        this.installation = installation;
        constructionMap.put(ConstructionType.TRAPEZE, createTrapezeInstallation());
        constructionMap.put(ConstructionType.VARIO_HOOK, createVarioHookInstallation());
        constructionMap.put(ConstructionType.DOUBLE_THREADED_SCREW_OBLIQUE, createDoubleThreadedScrewObliqueInstallation());
        constructionMap.put(ConstructionType.DOUBLE_THREADED_SCREW_FLAT, createDoubleThreadedScrewFlatInstallation());
        constructionMap.put(ConstructionType.DOUBLE_THREADED_ROD, createDoubleThreadedRodFlatInstallation());
    }

    /**
     * Creates construction material for installation based on the installation type
     * @return InstallationMaterial List
     */
    public List<InstallationMaterial> createInstallationConstructionMaterials(){
       return constructionMap.get(installation.getInstallationType()).apply(installation);
    }
    private Function<Installation, List<InstallationMaterial>> createTrapezeInstallation(){
        List<InstallationMaterial> materials = new ArrayList<>();
        return installation -> {
            setCommonMaterials(materials);
            InstallationMaterial trapeze = constructionMaterialCalculator.setTrapeze();

            materials.add(trapeze);
            materials.add(constructionMaterialCalculator.setScrewsForTrapeze(trapeze.getQuantity()));
            return materials;
        };
    }
    private Function<Installation, List<InstallationMaterial>> createVarioHookInstallation(){
        List<InstallationMaterial> materials = new ArrayList<>();
        return installation -> {
            InstallationMaterial profile = constructionMaterialCalculator.setProfile();
            setCommonMaterials(materials);
            materials.add(profile);
            materials.add(constructionMaterialCalculator.setProfileJoiner(profile.getQuantity()));
            InstallationMaterial hexagonScrew = constructionMaterialCalculator.setHexagonScrew(profile.getQuantity());
            InstallationMaterial varioHook = constructionMaterialCalculator.setVarioHook(hexagonScrew.getQuantity());
            materials.add(varioHook);
            materials.add(constructionMaterialCalculator.setScrewsForVarioHook(varioHook.getQuantity()));
            materials.add(hexagonScrew);
            materials.add(constructionMaterialCalculator.setHexagonNut(hexagonScrew.getQuantity()));
            return materials;
        };
    }
    private Function<Installation, List<InstallationMaterial>> createDoubleThreadedScrewObliqueInstallation(){
        List<InstallationMaterial> materials = new ArrayList<>();
        return installation -> {
            setDoubleThreadedBase(materials);
            return materials;
        };
    }
    private Function<Installation, List<InstallationMaterial>> createDoubleThreadedScrewFlatInstallation(){
        List<InstallationMaterial> materials = new ArrayList<>();
        return installation -> {
            setDoubleThreadedBase(materials);
            materials.add(constructionMaterialCalculator.setAngleBar());
            return materials;
        };
    }
    private Function<Installation, List<InstallationMaterial>> createDoubleThreadedRodFlatInstallation(){
        List<InstallationMaterial> materials = new ArrayList<>();
        return installation -> {
            InstallationMaterial profile = constructionMaterialCalculator.setProfile();
            setCommonMaterials(materials);
            materials.add(profile);
            materials.add(constructionMaterialCalculator.setProfileJoiner(profile.getQuantity()));
            InstallationMaterial threadedRod = constructionMaterialCalculator.setThreadedRod();
            materials.add(threadedRod);
            materials.add(constructionMaterialCalculator.setAngleBar());
            InstallationMaterial hexagonScrew = constructionMaterialCalculator.setHexagonScrew(profile.getQuantity());
            materials.add(hexagonScrew);
            materials.add(constructionMaterialCalculator.setHexagonNut(profile.getQuantity()));
            materials.add(constructionMaterialCalculator.setEpdm(threadedRod.getQuantity()));
            materials.add(constructionMaterialCalculator.setSleeve(threadedRod.getQuantity()));
            materials.add(constructionMaterialCalculator.setChemicalAnchor(threadedRod.getQuantity()));
            return materials;
        };
    }
    private void setDoubleThreadedBase(List<InstallationMaterial> materials){
        InstallationMaterial profile = constructionMaterialCalculator.setProfile();
        setCommonMaterials(materials);
        materials.add(profile);
        materials.add(constructionMaterialCalculator.setProfileJoiner(profile.getQuantity()));
        InstallationMaterial hexagonScrew = constructionMaterialCalculator.setHexagonScrew(profile.getQuantity());
        InstallationMaterial doubleThreadedScrew = constructionMaterialCalculator.setDoubleThreadedScrew(hexagonScrew.getQuantity());
        materials.add(hexagonScrew);
        materials.add(doubleThreadedScrew);
        materials.add(constructionMaterialCalculator.setAdapterOblique(doubleThreadedScrew.getQuantity()));
        materials.add(constructionMaterialCalculator.setHexagonNut(profile.getQuantity()));
    }
    private void setCommonMaterials(List<InstallationMaterial> materials){
        long edgeMaterial = constructionMaterialCalculator.calculateEdgeMaterial();
        materials.add(constructionMaterialService.createConstructionMaterial("End clamp %s mm".formatted(project.getModuleFrame()), constructionMaterialCalculator.calculateEndClamps(), installation));
        materials.add(constructionMaterialService.createConstructionMaterial("Mid clamp", constructionMaterialCalculator.calculateMidClamps(), installation));
        materials.add(constructionMaterialService.createConstructionMaterial("Allen screw %s mm".formatted(project.getModuleFrame() - ALLEN_SCREW_LENGTH_MOD),edgeMaterial, installation));
        materials.add(constructionMaterialService.createConstructionMaterial("Sliding key", edgeMaterial, installation));
    }
}
