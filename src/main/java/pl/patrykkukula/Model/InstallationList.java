package pl.patrykkukula.Model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static pl.patrykkukula.Model.Installation.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InstallationList {
    List<Installation> installationList = new ArrayList<>();

    public void addInstallation(Installation installation){
        installationList.add(installation);
    }
    public void getAllInstallations() {
        if (installationList.isEmpty()) {
            System.out.println("Lista instalacji jest pusta");
        }
        else {
            System.out.println("Liczba instalacji: " + count);
            for (int i = 0; i < installationList.size(); i++) {
                System.out.println("Lp. " + (i+1) + " " + installationList.get(i).getInstallationDetails());
            }
        }
    }
}
