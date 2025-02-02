package pl.patrykkukula.DataFormatters;
import pl.patrykkukula.Model.Installation;
public class InstallationFormatter {

    public static String format(Installation installation){
        return  "Typ instalacji: " + installation.getType() + System.lineSeparator() +
                "Moc instalacji: " + installation.getTotalPower()+ " kW" + System.lineSeparator() +
                "Liczba modułów: " + installation.getRowsAndModules() + System.lineSeparator();
    }
}
