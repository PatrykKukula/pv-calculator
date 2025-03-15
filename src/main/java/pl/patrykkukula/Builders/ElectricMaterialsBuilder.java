package pl.patrykkukula.Builders;
import lombok.NoArgsConstructor;
import pl.patrykkukula.Model.Installation;
import pl.patrykkukula.Model.InstallationList;
import java.util.*;

import static java.util.Collections.*;
import static pl.patrykkukula.Constants.ElectricConstants.FUSE_PER_DC_DISCONNECTOR;
import static pl.patrykkukula.Constants.ElectricConstants.POLES_PER_STRING;

@NoArgsConstructor
public class ElectricMaterialsBuilder {
    private final NavigableSet<Integer> currentSet = unmodifiableNavigableSet(new TreeSet<>(Set.of(6, 10, 16, 20, 25, 32, 40, 50)));
    private final NavigableSet<Integer> switchboardSize = unmodifiableNavigableSet(new TreeSet<>(Set.of(6, 12, 18, 24, 36, 48)));

    public Map<String, Integer> buildElectricMaterials(InstallationList installationList) {
        Map<String, Integer> electricMaterials = new LinkedHashMap<>();
            for (Installation installation : installationList.getInstallationList()) {
                addMaterial(electricMaterials, "Falownik fotowoltaiczny " + getInverterPower(installation) + " kW", 1);
                addMaterial(electricMaterials,"Kabel AC 5x" + installation.getAcCableCrossSection() + " mm2", installation.getAcCableLength());
                if (installation.getTotalPower() >= 10) addMaterial(electricMaterials,"Kabel DC 6 mm2", installation.getDcCableLength());
                else {
                    addMaterial(electricMaterials,"Kabel DC 4 mm2", installation.getDcCableLength());
                }
                addMaterial(electricMaterials,"Zabezpieczenie nadprądowe typ B" + setCurrentForBreaker(installation), 1);
                addMaterial(electricMaterials,"Zabezpieczenie nadprądowe typ C" + setCurrentForBreaker(installation), 1);
                addMaterial(electricMaterials,"Wyłącznik RCD " + setCurrentForBreaker(installation) + "/0,1A", 1);
                addMaterial(electricMaterials,"Ogranicznik przepieć AC 4P " + installation.getSurgeArresterType(), 1);
                addMaterial(electricMaterials,"Ogranicznik przepięć DC typ " + installation.getSurgeArresterType(), installation.getStrings());
                addMaterial(electricMaterials,"Rozłącznik bezpiecznikowy DC 2p", installation.getStrings());
                addMaterial(electricMaterials,"Wkładka bezpiecznikowa gG 15A", installation.getStrings() * FUSE_PER_DC_DISCONNECTOR);
                addMaterial(electricMaterials,"Rozdzielnica AC 12p", 1);
                addMaterial(electricMaterials,"Rozdzielnica DC " + setDcSwitchboardSize(installation) + "p", 1);
            }
        return electricMaterials;
    }
    private void addMaterial(Map<String, Integer> electricMaterials, String material, int quantity) {
        electricMaterials.put(material, electricMaterials.getOrDefault(material, 0) + quantity);
    }
    private int setCurrentForBreaker(Installation installation) {
       return currentSet.ceiling(getCurrent(installation));
    }
    private int getCurrent(Installation installation) {
        return (int) installation.getInverter().getCurrent();
    }
    private int getInverterPower(Installation installation) {
        return installation.getInverter().getInverterPower();
    }
    protected int setDcSwitchboardSize(Installation installation) {
        int minSize = installation.getStrings() * POLES_PER_STRING;
        return switchboardSize.ceiling(minSize);
    }
}
