package pl.patrykkukula;

import pl.patrykkukula.Model.Installation;
import pl.patrykkukula.Model.Inverter;
import pl.patrykkukula.Model.PvModule;

import java.util.List;

public class TestInstallation {

    public Installation createTestInstallation() {
        Installation installation = createBaseInstallation();
        Inverter inverter = new Inverter(installation);
        installation.setInverter(inverter);
        return installation;
    }
    private PvModule createTestModule() {
        return new PvModule(480,30,1050,2050);
    }
    private List<Integer> createTestRowsAndModules(){
        return List.of(9,9);
    }
    private Installation createBaseInstallation() {
        return new Installation(100,10, createTestRowsAndModules(), createTestModule(), "poziomo", 2, "T2");
    }
}
