package pl.patrykkukula.Builders;
import lombok.NoArgsConstructor;
import pl.patrykkukula.Model.Installation;
import pl.patrykkukula.Model.InstallationList;
import java.util.*;
import static pl.patrykkukula.Constants.ElectricConstants.FUSE_PER_DC_DISCONNECTOR;
import static pl.patrykkukula.Constants.ElectricConstants.POLES_PER_STRING;

@NoArgsConstructor
public class ElectricListBuilder {
    private final Map<String, Integer> electricMaterials = new LinkedHashMap<>();
    private static final NavigableSet<Integer> currentSet = Collections.unmodifiableNavigableSet(new TreeSet<>(Set.of(6, 10, 16, 20, 25, 32, 40, 50)));
    private static final NavigableSet<Integer> switchboardSize = Collections.unmodifiableNavigableSet(new TreeSet<>(Set.of(6, 12, 18, 24, 36, 48)));

    protected Map<String, Integer> buildElectricMaterials(InstallationList installationList) {
        for (Installation installation : installationList.getInstallationList()) {
            addMaterial("Falownik fotowoltaiczny " + installation.getInverter().getPower() + " kW", 1);
            addMaterial("Falownik fotowoltaiczny " + installation.getInverter().getPower() + " kW", 1);
            addMaterial("Kabel AC 5x" + installation.getAcCableCrossSection() + " mm2", installation.getAcCable());
            if (installation.getTotalPower() >= 10) addMaterial("Kabel DC 6 mm2", installation.getDcCable());
            else {
                addMaterial("Kabel DC 4 mm2", installation.getDcCable());
            }
            addMaterial("Zabezpieczenie nadprądowe typ B" + currentSet.ceiling(getCurrent(installation)), 1);
            addMaterial("Zabezpieczenie nadprądowe typ C" + currentSet.ceiling(getCurrent(installation)), 1);
            addMaterial("Wyłącznik RCD " + currentSet.ceiling(getCurrent(installation)) + "/0,1A", 1);
            addMaterial("Ogranicznik przepieć AC 4P" + installation.getSurgeArrester(), 1);
            addMaterial("Ogranicznik przepięć DC typ " + installation.getSurgeArrester(), installation.getStrings());
            addMaterial("Rozłącznik bezpiecznikowy DC 2p", installation.getStrings());
            addMaterial("Wkładka bezpiecznikowa gG 15A", installation.getStrings() * FUSE_PER_DC_DISCONNECTOR);
            addMaterial("Rozdzielnica AC 12p", 1);
            addMaterial("Rozdzielnica DC " + setDcSwitchboardSize(installation) + "p", 1);
        }
        return electricMaterials;
    }

    private void addMaterial(String material, int quantity) {
        electricMaterials.put(material, electricMaterials.getOrDefault(material, 0) + quantity);
    }

    private int getCurrent(Installation installation) {
        return (int) installation.getInverter().getCurrent();
    }

    private int setDcSwitchboardSize(Installation installation) {
        int minSize = installation.getStrings() * POLES_PER_STRING;
        return switchboardSize.ceiling(minSize);
    }

}
