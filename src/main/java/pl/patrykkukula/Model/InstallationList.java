package pl.patrykkukula.Model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.patrykkukula.DataFormatters.InstallationFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class InstallationList {
    List<Installation> installationList = new ArrayList<>();

    public InstallationList(List<Installation> installationList) {
        this.installationList = installationList;
    }

    public void addInstallation(Installation installation){
        installationList.add(installation);
    }

    public void getAllInstallations() {
        if (installationList.isEmpty()) {
            System.out.println("Lista instalacji jest pusta");
        }
        else {
            System.out.println("Liczba instalacji: " + Installation.count);
            for (int i = 0; i < installationList.size(); i++) {
                System.out.println("Lp. " + i + " " + InstallationFormatter.format(installationList.get(i)));
            }
        }
    }
}
