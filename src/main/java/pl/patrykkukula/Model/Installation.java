package pl.patrykkukula.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.patrykkukula.Constants.ConstructionConstants;
import pl.patrykkukula.DataPresentationFormatters.InstallationFormatter;
import pl.patrykkukula.Model.ConstructionModel.Abstract.AbstractConstructionModel;
import java.util.InputMismatchException;
import java.util.List;
import static pl.patrykkukula.Constants.ConstructionConstants.*;

@Data
@NoArgsConstructor
public class Installation {
    private AbstractConstructionModel model;
    private String type;
    private String orientation;
    private PvModule module;
    private double totalPower;
    private int dcCable;
    private int acCable;
    private List<Integer> rowsAndModules;

    public Installation(int acCable, int dcCable, List<Integer> modules, PvModule module, String orientation) {
        this.acCable = acCable;
        this.dcCable = dcCable;
        this.rowsAndModules = modules;
        this.module = module;
        this.orientation = orientation;
    }

    public void setTotalPower() {
        int modulesQty = getModulesQty();
        int modulePower = module.getPower();
        this.totalPower = modulesQty * modulePower;
    }
    public int getModulesQty() {
        if (rowsAndModules==null || rowsAndModules.isEmpty()) throw new IllegalArgumentException("Do instalacji nie dodano żadnych modułów");
        return rowsAndModules.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
    public String getInstallationDetails() {
        return InstallationFormatter.format(this);
    }
    public double calculateProfile(){
        checkNotNull(this);
        int modulesQty = getModulesQty();
        double calculationLenght = 0.0;
        if (this.orientation.equals("pionowo")){
            calculationLenght = getModule().getWidth();
        } else if (this.orientation.equals("poziomo")) {
            calculationLenght = getModule().getLength();
        }
        return modulesQty*calculationLenght * 2 * SURPLUS_FACTOR / MAP_MILLIMETER_TO_METER; // *2 because it takes 2 profiles for 1 module
    }
    public int calculateEndClamp(){
        checkNotNull(this);
        return getRowsAndModules().size()*END_CLAMPS_PER_ROW;
    }

    public int getTotalEdge() {
        checkNotNull(this);
        return getRowsAndModules().stream()
                .mapToInt(row -> row * 2 + 2) // this is general pattern for calculating some material based on how many edges are present in installation
                .sum();
    }
    public double calculateAngleBarLength(){
        checkNotNull(this);
        return calculateAngleBarQty() * ConstructionConstants.SINGLE_ANGLE_BAR_LENGTH;
        // this is general pattern for calculating angle bar lenght based on how many edges are present in installation
    }
    public int calculateAngleBarQty(){
        checkNotNull(this);
        return getRowsAndModules().stream()
                .mapToInt(row -> (row+1))// this is general pattern for calculating construction circles based on how many edges are present in installation
                .sum();
    }
    private void checkNotNull(Installation installation){
        if (installation==null) throw new InputMismatchException("Nie dodano instalacji");
    }
    public <T extends AbstractConstructionModel> void setModel(T model){
        this.model = model;
    }
}

