package pl.patrykkukula.DataPresentationFormatters;
import pl.patrykkukula.Model.Installation;
public class InstallationFormatter {

    public static String format(Installation installation){
        return "Typ instalacji: " + installation.getType() + System.lineSeparator() +
                "Moc instalacji: " + installation.getTotalPower() / 1000 + " kW" + System.lineSeparator() + // 1000 is here to map watts for kilowatts
                "Liczba modułów: " + installation.getRowsAndModules() + System.lineSeparator();
    }
}
