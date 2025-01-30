package pl.patrykkukula.DataFormatters;
import pl.patrykkukula.Model.Installation;
import static pl.patrykkukula.Constants.ConstructionConstants.CONVERT_UNIT_FROM_KILOS;
public class InstallationFormatter {

    public static String format(Installation installation){
        return  "Typ instalacji: " + installation.getType() + System.lineSeparator() +
                "Moc instalacji: " + installation.getTotalPower() / CONVERT_UNIT_FROM_KILOS + " kW" + System.lineSeparator() +
                "Liczba modułów: " + installation.getRowsAndModules() + System.lineSeparator();
    }
}
