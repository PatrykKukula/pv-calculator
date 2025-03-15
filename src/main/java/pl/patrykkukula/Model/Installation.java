package pl.patrykkukula.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
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
    private int dcCableLength;
    private int acCableLength;
    private List<Integer> rowsAndModules;
    private int strings;
    private String surgeArresterType;
    private Inverter inverter;
    public static int count;

    public Installation(int acCable, int dcCable, List<Integer> modules, PvModule module, String orientation, int strings, String surgeArrester) {
        this.acCableLength = acCable;
        this.dcCableLength = dcCable;
        this.rowsAndModules = modules;
        this.module = module;
        this.orientation = orientation;
        this.strings = strings;
        this.surgeArresterType = surgeArrester;
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
        return  "Typ instalacji: " + type + System.lineSeparator() +
                "Moc instalacji: " + totalPower+ " kW" + System.lineSeparator() +
                "Liczba modułów: " + getModulesQty() + System.lineSeparator();
    }
    public double calculateProfileLength(){
        int modulesQty = getModulesQty();
        double calculationLength = 0.0;
        if (orientation.equals("pionowo")){
            calculationLength = getModule().getWidth();
        } else if (orientation.equals("poziomo")) {
            calculationLength = getModule().getLength();
        }
        return round((modulesQty * calculationLength * 2 * SURPLUS_FACTOR / CONVERT_UNIT_FROM_KILOS) * 100 / 100); // *2 because it takes 2 profiles for 1 module
    }
    public int calculateEndClamp(){
        return getRowsAndModules().size() * END_CLAMPS_PER_ROW;
    }
    public int calculateTotalEdge() {
        return getRowsAndModules().stream()
                .mapToInt(row -> row * 2 + 2) // this is general pattern for calculating some material based on how many edges are present in installation for example allen screw
                .sum();
    }
    public int calculateAngleBarLength(){
        return (int) ceil(calculateAngleBarQty() * ANGLE_BAR_LENGTH);
        // this is general pattern for calculating angle bar length based on how many edges are present in installation
    }
    public int calculateAngleBarQty(){
        return getRowsAndModules().stream()
                .mapToInt(row -> (row+1))// this is general pattern for calculating construction triangles based on how many edges are present in installation
                .sum();
    }
    public double getAcCableCrossSection(){
        double voltageDrop;
        double returnValue = 0;
        for (Double crossSectionValue : crossSectionValues) {
            voltageDrop = (100 * sqrt(3.0) * inverter.getCurrent() * acCableLength * COS_FI) / (COOPER_CONDUCTIVITY * crossSectionValue * THREE_PHASE_VOLTAGE);
            returnValue = crossSectionValue;
            if (voltageDrop <= 1.0) break; //allowed voltage drop is 1%, and you want to have the lowest possible cross-section value
        }
        return returnValue;
    }
    private static final List<Double> crossSectionValues = List.of(2.5, 4.0,6.0,10.0,16.0,25.0); // typical cables crossSections available on market that are enough within this app
}

