package pl.patrykkukula.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.patrykkukula.Constants.ConstructionConstants;
import pl.patrykkukula.DataFormatters.InstallationFormatter;
import pl.patrykkukula.Model.ConstructionModel.Abstract.AbstractConstructionModel;
import java.util.InputMismatchException;
import java.util.List;

import static java.lang.Math.*;
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
    public static int count;

    public Installation(int acCable, int dcCable, List<Integer> modules, PvModule module, String orientation) {
        this.acCable = acCable;
        this.dcCable = dcCable;
        this.rowsAndModules = modules;
        this.module = module;
        this.orientation = orientation;
        count++;
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
        int modulesQty = getModulesQty();
        double calculationLength = 0.0;
        if (this.orientation.equals("pionowo")){
            calculationLength = getModule().getWidth();
        } else if (this.orientation.equals("poziomo")) {
            calculationLength = getModule().getLength();
        }
        return modulesQty* calculationLength * 2 * SURPLUS_FACTOR / CONVERT_UNIT_FROM_KILOS; // *2 because it takes 2 profiles for 1 module
    }
    public void setType(int constructionType){
        if (constructionType == 1) this.type = "Mostki trapezowe";
        else if (constructionType == 2) this.type = "Śruba dwugwintowa";
        else if (constructionType == 3) this.type = "Dach płaski - śruba dwugwintowa";
        else if (constructionType == 4) this.type = "Dach płaski - pręt gwintowany";
        else if (constructionType == 5) this.type = "Hak vario";
    }
    public int calculateEndClamp(){
        return getRowsAndModules().size()*END_CLAMPS_PER_ROW;
    }
    public int getTotalEdge() {
        return getRowsAndModules().stream()
                .mapToInt(row -> row * 2 + 2) // this is general pattern for calculating some material based on how many edges are present in installation
                .sum();
    }
    public int calculateAngleBarLength(){
        return (int) ceil(calculateAngleBarQty() * ConstructionConstants.SINGLE_ANGLE_BAR_LENGTH);
        // this is general pattern for calculating angle bar lenght based on how many edges are present in installation
    }
    public int calculateAngleBarQty(){
        return getRowsAndModules().stream()
                .mapToInt(row -> (row+1))// this is general pattern for calculating construction circles based on how many edges are present in installation
                .sum();
    }
    public <T extends AbstractConstructionModel> void setModel(T model){
        this.model = model;
    }
}

