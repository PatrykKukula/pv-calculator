package pl.patrykkukula.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.patrykkukula.DataFormatters.InstallationFormatter;
import pl.patrykkukula.Model.ConstructionModel.Abstract.AbstractConstructionModel;

import java.util.List;

import static java.lang.Math.*;
import static pl.patrykkukula.Constants.ConstructionConstants.*;
import static pl.patrykkukula.Constants.ElectricConstants.*;

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
    private int strings;
    private String surgeArrester;
    private Inverter inverter;
    public static int count;

    public Installation(int acCable, int dcCable, List<Integer> modules, PvModule module, String orientation, int strings, String surgeArrester) {
        this.acCable = acCable;
        this.dcCable = dcCable;
        this.rowsAndModules = modules;
        this.module = module;
        this.orientation = orientation;
        this.strings = strings;
        this.surgeArrester = surgeArrester;
        setTotalPower();
        count++;
    }
    private void setTotalPower() {
        int modulesQty = getModulesQty();
        int modulePower = module.getPower();
        this.totalPower = (double)modulesQty * modulePower / CONVERT_UNIT_FROM_KILOS;
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
    public double calculateProfileLength(){
        int modulesQty = getModulesQty();
        double calculationLength = 0.0;
        if (orientation.equals("pionowo")){
            calculationLength = getModule().getWidth();
        } else if (orientation.equals("poziomo")) {
            calculationLength = getModule().getLength();
        }
        return round((modulesQty* calculationLength * 2 * SURPLUS_FACTOR / CONVERT_UNIT_FROM_KILOS) * 100 / 100); // *2 because it takes 2 profiles for 1 module
    }
    public void setType(int constructionType){
        if (constructionType == 1) this.type = "Mostki trapezowe";
        else if (constructionType == 2) this.type = "Śruba dwugwintowa";
        else if (constructionType == 3) this.type = "Dach płaski - śruba dwugwintowa";
        else if (constructionType == 4) this.type = "Dach płaski - pręt gwintowany";
        else if (constructionType == 5) this.type = "Hak vario";
        else throw new IllegalArgumentException("Nieznany typ instalacji");
    }
    public int calculateEndClamp(){
        return getRowsAndModules().size()*END_CLAMPS_PER_ROW;
    }
    public int calculateTotalEdge() {
        return getRowsAndModules().stream()
                .mapToInt(row -> row * 2 + 2) // this is general pattern for calculating some material based on how many edges are present in installation for example allen screw
                .sum();
    }
    public int calculateAngleBarLength(){
        return (int) ceil(calculateAngleBarQty() * SINGLE_ANGLE_BAR_LENGTH);
        // this is general pattern for calculating angle bar lenght based on how many edges are present in installation
    }
    public int calculateAngleBarQty(){
        return getRowsAndModules().stream()
                .mapToInt(row -> (row+1))// this is general pattern for calculating construction circles based on how many edges are present in installation
                .sum();
    }
    public double getAcCableCrossSection(){
        double voltageDrop;
        double returnValue = 0;
        for (Double crossSectionValue : crossSectionValues) {
            voltageDrop = (100 * sqrt(3.0) * getInverter().getCurrent() * acCable * COS_FI) / (COOPER_CONDUCTIVITY * crossSectionValue * THREE_PHASE_VOLTAGE);
            returnValue = crossSectionValue;
            if (voltageDrop <= 1.0) break; //allowed voltage drop is 1%
        }
        return returnValue;
    }
    private static final List<Double> crossSectionValues = List.of(2.5, 4.0,6.0,10.0,16.0,25.0); // typical cables crossSections available on market

    public <T extends AbstractConstructionModel> void setModel(T model){
        this.model = model;
    }


}

