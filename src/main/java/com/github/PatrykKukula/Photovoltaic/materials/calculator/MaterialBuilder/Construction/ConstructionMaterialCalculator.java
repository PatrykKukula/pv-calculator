package com.github.PatrykKukula.Photovoltaic.materials.calculator.MaterialBuilder.Construction;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ModuleOrientation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Installation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.InstallationMaterial;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Project;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Row;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.MaterialService;
import lombok.extern.slf4j.Slf4j;

import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ConstructionMaterialConstants.*;

@Slf4j
public class ConstructionMaterialCalculator {
    private final MaterialService constructionMaterialService;
    private final Installation installation;
    private final Project project;

    public ConstructionMaterialCalculator(MaterialService constructionMaterialService, Installation installation, Project project){
        this.constructionMaterialService = constructionMaterialService;
        this.installation = installation;
        this.project = project;
    }
    /*
        Profile length will differ depends on the module orientation.
        The surplus factor is due to the wasted material by cutting it
     */
    public InstallationMaterial setProfile(){
        long moduleQuantity = installation.getRows().stream().mapToLong(Row::getModuleQuantity).sum();
        if(installation.getModuleOrientation().equals(ModuleOrientation.VERTICAL)) {
            return constructionMaterialService.createConstructionMaterial("Aluminium profile 40x40", Math.round(moduleQuantity * 2 * project.getModuleWidth() * SURPLUS_FACTOR / CONVERT_UNIT_FROM_MM_TO_M));
        }
        return constructionMaterialService.createConstructionMaterial("Aluminium profile 40x40", Math.round(moduleQuantity * 2 * project.getModuleLength() * SURPLUS_FACTOR / CONVERT_UNIT_FROM_MM_TO_M));
    }
    /*
        this is not 100% accurate because it depends on total length of the row, however it gives a good starting point
     */
    public InstallationMaterial setProfileJoiner(long profile){
        return constructionMaterialService.createConstructionMaterial("Profile joiner", Math.round(((double)profile / PROFILE_LENGTH) / CONVERT_UNIT_FROM_MM_TO_M));
    }
    public InstallationMaterial setHexagonScrew(long profile){
        switch (installation.getInstallationType()){
            case DOUBLE_THREADED_SCREW_OBLIQUE, VARIO_HOOK -> {
                return constructionMaterialService.createConstructionMaterial("Hexagon screw M10x250", Math.round((double)profile / DISTANCE_BETWEEN_RAFTER));
            }
            case DOUBLE_THREADED_SCREW_FLAT, DOUBLE_THREADED_ROD -> {
                return constructionMaterialService.createConstructionMaterial("Hexagon screw M10x250", calculateTotalEdges() * HEXAGON_SCREW_PER_EDGE);
            }
            default -> throw new RuntimeException("Error during calculating hexagon screws. This shouldn't happen - please contact administrator");
        }
    }
    public InstallationMaterial setHexagonNut(long profile){
        switch (installation.getInstallationType()){
            case DOUBLE_THREADED_SCREW_OBLIQUE, VARIO_HOOK -> {

                return constructionMaterialService.createConstructionMaterial("Hexagon nut M10", Math.round((double)profile / DISTANCE_BETWEEN_RAFTER));
            }
            case DOUBLE_THREADED_SCREW_FLAT -> {
                return constructionMaterialService.createConstructionMaterial("Hexagon nut M10",calculateTotalEdges() * HEXAGON_NUT_FOR_THREADED_ROD);
            }
            case DOUBLE_THREADED_ROD -> {
                return constructionMaterialService.createConstructionMaterial("Hexagon nut M10",calculateTotalEdges() * HEXAGON_NUT_FOR_DOUBLE_THREADED_FLAT);
            }
            default -> throw new RuntimeException("Error during calculating hexagon nuts. This shouldn't happen - please contact administrator");
        }
    }
    /*
        Vario hooks are always one to one with hexagon screw
     */
    public InstallationMaterial setVarioHook(long hexagonScrew){
        return constructionMaterialService.createConstructionMaterial("Vario hook", hexagonScrew);
    }
    /*
        For oblique roofs double-headed screw and adapters are always one to one with hexagon screw
   */
    public InstallationMaterial setDoubleThreadedScrew(long hexagonScrew){
        return constructionMaterialService.createConstructionMaterial("Double threaded screw L=250mm", hexagonScrew);
    }
    public InstallationMaterial setAdapterOblique(long doubleThreadedScrew){
        return constructionMaterialService.createConstructionMaterial("Adapter", doubleThreadedScrew);
    }
    public InstallationMaterial setScrewsForVarioHook(long varioHook){
        return constructionMaterialService.createConstructionMaterial("Screws for vario hook", varioHook * SCREWS_PER_VARIO);
    }
    /*
        This is always equals
     */
    public InstallationMaterial setTrapeze(){
        return constructionMaterialService.createConstructionMaterial("Trapeze", calculateEdgeMaterial());
    }
    public InstallationMaterial setScrewsForTrapeze(long trapeze){
        return constructionMaterialService.createConstructionMaterial("Trapeze screws", trapeze * SCREWS_PER_TRAPEZE);
    }
    public InstallationMaterial setAngleBar(){
        return constructionMaterialService.createConstructionMaterial("Aluminium angle bar 40x3", Math.round(calculateTotalEdges() * ANGLE_BAR_LENGTH));
    }
    public InstallationMaterial setThreadedRod(){
        return constructionMaterialService.createConstructionMaterial("Threaded rod M10", calculateTotalEdges() * THREADED_ROD_PER_ANGLE_BAR);
    }
    public InstallationMaterial setEpdm(Long threadedRod){
        return constructionMaterialService.createConstructionMaterial("EPDM M10", threadedRod);
    }
    public InstallationMaterial setChemicalAnchor(Long threadedRod){
        return constructionMaterialService.createConstructionMaterial("Chemical anchor", (Math.round((double)threadedRod / THREADED_ROD_PER_CHEMICAL_ANCHOR)));
    }
    public InstallationMaterial setSleeve(Long threadedRod){
        return constructionMaterialService.createConstructionMaterial("Sleeve for threaded rod", threadedRod);
    }
    public long calculateEndClamps(){
        return installation.getRows().stream().mapToLong(Row::getRowNumber).sum() * END_CLAMPS_PER_ROW;
    }
    /*
        General pattern for calculating mid-clamps, this pattern will always be exact number of needed mid-clamps
     */
    public long calculateMidClamps(){
        return installation.getRows().stream().mapToLong(row -> row.getModuleQuantity() * 2 - 2).sum();
    }
    /*
        each row always has one more edge than modules
     */
    private long calculateTotalEdges(){
        return installation.getRows().stream().mapToLong(row -> row.getModuleQuantity() + 1).sum();
    }
    /**
     * @return amount of materials that simply rely on the total edge number of all the rows in installation for example sliding key or allen screw
     */
    public long calculateEdgeMaterial(){
        return installation.getRows().stream().mapToLong(row -> row.getModuleQuantity() * 2 + 2).sum();
    }
}
