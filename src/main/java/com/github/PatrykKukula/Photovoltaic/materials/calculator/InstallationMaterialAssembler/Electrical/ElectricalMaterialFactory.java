package com.github.PatrykKukula.Photovoltaic.materials.calculator.InstallationMaterialAssembler.Electrical;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ConstructionMaterialConstants;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ModuleOrientation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Installation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.InstallationMaterial;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Row;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.MaterialService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ConstructionMaterialConstants.*;
import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ElectricalMaterialConstants.*;
import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.PhaseNumber.THREE_PHASE;
import static java.lang.Math.sqrt;


@Slf4j
public class ElectricalMaterialFactory {
    private final MaterialService materialService;
    private final Installation installation;
    private final Long modulePower;
    private final NavigableMap<Integer, String> overcurrentProtectionCurrentMapThreePhased = new TreeMap<>();
    private final NavigableMap<Double, String> overcurrentProtectionCurrentMapSinglePhased = new TreeMap<>();
    private final NavigableMap<Double, Integer> inverterMapThreePhased = new TreeMap<>();
    private final NavigableMap<Double, Double> inverterMapSinglePhased = new TreeMap<>();
    private final NavigableMap<Double, Integer> differentialProtectionMap = new TreeMap<>();
    private final NavigableMap <Integer, Integer> dcSwitchboardMap = new TreeMap<>();
    private final List<Double> crossSectionValues = List.of(2.5, 4.0,6.0,10.0,16.0,25.0);
    private int overcurrentProtectionCurrent;
    private Double inverterPower;
    private final ProjectDto project;


    public ElectricalMaterialFactory(MaterialService materialService, Installation installation, Long modulePower, ProjectDto project){
        this.materialService = materialService;
        this.installation = installation;
        this.modulePower = modulePower;
        this.project = project;
        fillProtectionCurrentMap();
        fillInverterMap();
        fillDifferentialProtectionMap();
        fillDcSwitchboardMap();
        setInverterPower();
    }
    public InstallationMaterial createPhotovoltaicModule(){
        return materialService.createElectricalMaterial("Photovoltaic module", calculateModuleQuantity(), installation);
    }
    public InstallationMaterial createInverter(){
        return installation.getPhaseNumber() == THREE_PHASE ? createThreePhasedInverter() :createSinglePhasedInverter();
    }
    public InstallationMaterial createDcSwitchboard(){
        Integer poles = dcSwitchboardMap.floorEntry(((installation.getStrings() * POLES_PER_STRING))).getValue();
        return materialService.createElectricalMaterial("DC switchboard %sP".formatted(poles), 1L, installation);
    }
    public InstallationMaterial createAcSwitchboard(){
        return materialService.createElectricalMaterial("AC switchboard 12P", 1L, installation);
    }
    public InstallationMaterial createAcCable(){
        Long acCableLength = installation.getAcCableLength().longValue();
        setCurrent();
        return installation.getPhaseNumber() == THREE_PHASE ?
                materialService.createElectricalMaterial(calculateAcCableCrossSectionThreePhased(acCableLength), acCableLength, installation)
         : materialService.createElectricalMaterial(calculateAcCableCrossSectionSinglePhased(acCableLength), acCableLength, installation);
    }
    public InstallationMaterial createDcCable(){
        int crossSection = calculateTotalPower() <= 10 ? 4 : 6;
        long value = installation.getModuleOrientation() == ModuleOrientation.VERTICAL ?
                (project.getModuleWidth() / CONVERT_UNIT_FROM_MM_TO_M) * installation.getRows().stream().mapToLong(Row::getModuleQuantity).sum()*2
                        + (long)installation.getStrings() * installation.getDcCableLength() + installation.getRows().size()*5L:
                 (project.getModuleLength() / CONVERT_UNIT_FROM_MM_TO_M) * installation.getRows().stream().mapToLong(Row::getModuleQuantity).sum()*2
                        + (long)installation.getStrings() * installation.getDcCableLength() + installation.getRows().size()*5L ;
        return materialService.createElectricalMaterial("DC cable %s mm2".formatted(crossSection), value, installation);
    }
    public InstallationMaterial createDcFuse(){
        long quantity = installation.getStrings() * DC_FUSE_PER_HOLDER;
        return materialService.createElectricalMaterial("DC fuse 15A", quantity, installation);
    }
    public InstallationMaterial createDcFuseHolder(){
        Integer quantity = installation.getStrings();
        return materialService.createElectricalMaterial("DC fuse holder 2p", quantity.longValue(), installation);
    }
    public InstallationMaterial createDcSurgeArresters(){
        String type = installation.isLightingProtection() ? "T1+2" : "T2";
        Integer quantity = installation.getStrings();
        return materialService.createElectricalMaterial("Surge arrester DC 3P %s".formatted(type), quantity.longValue(), installation);
    }
    public InstallationMaterial createAcSurgeArrester(){
        String type = installation.isLightingProtection() ? "T1+2" : "T2";
        String phase = installation.getPhaseNumber() == THREE_PHASE ? "4P" : "2P";
        return materialService.createElectricalMaterial("Surge arrester AC %s %s".formatted(phase, type), 1L, installation);
    }
    public InstallationMaterial createLgyCable(){
        String value = installation.isLightingProtection() ? "16" : "6";
        return materialService.createElectricalMaterial("LgY cable %s mm2".formatted(value), installation.getLgyCableLength().longValue(), installation);
    }
    public InstallationMaterial createDifferentialCircuitBreaker(){
        String phase = installation.getPhaseNumber() == THREE_PHASE ? "4P" : "2P";
        setCurrent();
        Integer current = differentialProtectionMap.ceilingEntry(inverterPower).getValue();
        log.info("Differential current value:{} ", current);
        return materialService.createElectricalMaterial("Differential circuit breaker %s %s/0,1A".formatted(phase, current), 1L, installation);
    }
    public InstallationMaterial createOvercurrentCircuitBreakerC(){
        String phase = installation.getPhaseNumber() == THREE_PHASE ? "3P" : "1P";
        setCurrent();
        return materialService.createElectricalMaterial("Overcurrent circuit breaker %s C%s".formatted(phase, overcurrentProtectionCurrent), 1L, installation);
    }
    public InstallationMaterial createOvercurrentCircuitBreakerB(){
        String phase = installation.getPhaseNumber() == THREE_PHASE ? "3P" : "1P";
        setCurrent();
        return materialService.createElectricalMaterial("Overcurrent circuit breaker %s B%s".formatted(phase, overcurrentProtectionCurrent), 1L, installation);
    }
    private InstallationMaterial createThreePhasedInverter(){
        Integer inverterPower = inverterMapThreePhased.ceilingEntry(calculateTotalPower()).getValue();
        log.info("3 Phased inverter power:{} ", inverterPower);
        return materialService.createElectricalMaterial("3 Phased inverter %s kW".formatted(inverterPower), 1L, installation);
    }
    private InstallationMaterial createSinglePhasedInverter(){
        Double inverterPower = inverterMapSinglePhased.ceilingEntry(calculateTotalPower()).getValue();
        String formattedPower = String.format("%.2f", inverterPower).replace(',', '.');
        log.info("1 Phased inverter power:{} ",formattedPower);
        return materialService.createElectricalMaterial("1 Phased inverter %s kW".formatted(formattedPower), 1L, installation);
    }
    private Long calculateModuleQuantity(){
        return installation.getRows().stream().mapToLong(Row::getModuleQuantity).sum();
    }
    private void setInverterPower(){
        if (installation.getPhaseNumber() == THREE_PHASE) {
            inverterPower = (double)inverterMapThreePhased.ceilingEntry(calculateTotalPower()).getValue();
        }
        else inverterPower = inverterMapSinglePhased.ceilingEntry(calculateTotalPower()).getValue();
    }
    private Double calculateTotalPower(){
        return installation.getRows().stream().mapToDouble(row -> (double)(row.getModuleQuantity() * modulePower)).sum() / CONVERT_W_TO_KW;
    }
    /*
         Get cable with the least possible cross-section while achieving voltage drop below 1 % that is common standard
     */
    private String calculateAcCableCrossSectionThreePhased(Long acCableLength){
        double voltageDrop;
        double crossSection = 0;
        for (Double crossSectionValue : crossSectionValues) {
            voltageDrop = (100 * sqrt(3.0) * overcurrentProtectionCurrent * acCableLength * COS_FI) / (COOPER_CONDUCTIVITY * crossSectionValue * THREE_PHASE_VOLTAGE);
            crossSection = crossSectionValue;
            if (voltageDrop <= 1.0) break; //allowed voltage drop is 1%, and you want to have the lowest possible cross-section value
        }
        return "AC cable 5x" + crossSection + " mm2";
    }
    /*
     Get cable with the least possible cross-section while achieving voltage drop below 1 % that is common standard
 */
    private String calculateAcCableCrossSectionSinglePhased(Long acCableLength){
        double voltageDrop;
        double crossSection = 0;
        for (Double crossSectionValue : crossSectionValues) {
            voltageDrop = (100 * overcurrentProtectionCurrent * acCableLength * COS_FI) / (COOPER_CONDUCTIVITY * crossSectionValue * SINGLE_PHASE_VOLTAGE);
            crossSection = crossSectionValue;
            if (voltageDrop <= 1.0) break; //allowed voltage drop is 1%, and you want to have the lowest possible cross-section value
        }
        return "AC cable 3x" + crossSection + " mm2";
    }
    private void setCurrent(){
        if (installation.getPhaseNumber() == THREE_PHASE){
            overcurrentProtectionCurrent = Integer.parseInt(overcurrentProtectionCurrentMapThreePhased.ceilingEntry(inverterMapThreePhased.ceilingEntry(calculateTotalPower()).getValue()).getValue());
        }
        else {
            overcurrentProtectionCurrent = Integer.parseInt(overcurrentProtectionCurrentMapSinglePhased.ceilingEntry(inverterMapSinglePhased.ceilingEntry(calculateTotalPower()).getValue()).getValue());
        }
        log.info("Overcurrent protection value:{} ", overcurrentProtectionCurrent);
    }
    private void fillProtectionCurrentMap(){
        overcurrentProtectionCurrentMapThreePhased.put(3, "6");
        overcurrentProtectionCurrentMapThreePhased.put(4, "10");
        overcurrentProtectionCurrentMapThreePhased.put(5, "10");
        overcurrentProtectionCurrentMapThreePhased.put(6, "16");
        overcurrentProtectionCurrentMapThreePhased.put(8, "16");
        overcurrentProtectionCurrentMapThreePhased.put(10, "20");
        overcurrentProtectionCurrentMapThreePhased.put(12, "25");
        overcurrentProtectionCurrentMapThreePhased.put(15, "32");
        overcurrentProtectionCurrentMapThreePhased.put(17, "32");
        overcurrentProtectionCurrentMapThreePhased.put(20, "40");
        overcurrentProtectionCurrentMapThreePhased.put(25, "50");
        overcurrentProtectionCurrentMapThreePhased.put(30, "50");
        overcurrentProtectionCurrentMapThreePhased.put(40, "80");
        overcurrentProtectionCurrentMapThreePhased.put(50, "100");
        overcurrentProtectionCurrentMapSinglePhased.put(2.0, "10");
        overcurrentProtectionCurrentMapSinglePhased.put(3.0, "16");
        overcurrentProtectionCurrentMapSinglePhased.put(3.68, "20");
    }
    private void fillInverterMap(){
        inverterMapThreePhased.put(3.3, 3);
        inverterMapThreePhased.put(4.4, 4);
        inverterMapThreePhased.put(5.5, 5);
        inverterMapThreePhased.put(6.6, 6);
        inverterMapThreePhased.put(8.8, 8);
        inverterMapThreePhased.put(11.0, 10);
        inverterMapThreePhased.put(13.2, 12);
        inverterMapThreePhased.put(16.5, 15);
        inverterMapThreePhased.put(18.7, 17);
        inverterMapThreePhased.put(22.0, 20);
        inverterMapThreePhased.put(27.5, 25);
        inverterMapThreePhased.put(33.0, 30);
        inverterMapThreePhased.put(44.0, 40);
        inverterMapThreePhased.put(55.0, 50);
        //think about double here
        inverterMapSinglePhased.put(2.2, 2.0);
        inverterMapSinglePhased.put(3.3, 3.0);
        inverterMapSinglePhased.put(3.6, 3.6);
    }
    private void fillDifferentialProtectionMap(){
        differentialProtectionMap.put(10.0, 20);
        differentialProtectionMap.put(20.0, 40);
        differentialProtectionMap.put(25.0, 50);
        differentialProtectionMap.put(40.0, 80);
        differentialProtectionMap.put(50.0, 100);
    }
    private void fillDcSwitchboardMap(){
        dcSwitchboardMap.put(0, 8);
        dcSwitchboardMap.put(9, 12);
        dcSwitchboardMap.put(13, 24);
        dcSwitchboardMap.put(25, 36);
        dcSwitchboardMap.put(37, 48);
    }
}
